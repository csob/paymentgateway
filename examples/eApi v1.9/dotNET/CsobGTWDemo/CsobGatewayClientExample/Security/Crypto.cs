using System.Security.Cryptography;
using System.Text;

namespace CsobGatewayClientExample.Security;

public class Crypto
{
    private readonly byte[] _pemKey;

    private readonly byte[] _publicKey;

    public Crypto(string merchantId, string privateKeyFilePath, string publicKeyPath)
    {
        MerchantId = merchantId;
        _pemKey = DecodePemKey(File.ReadAllText(privateKeyFilePath)) ?? throw new InvalidOperationException();
        _publicKey = DecodePemKey(File.ReadAllText(publicKeyPath)) ?? throw new InvalidOperationException();
    }

    internal string MerchantId { get; }

    public string Sign(string inputData)
    {
        //Decode private key
        using var rsaCryptoServiceProvider = DecodeRSAPrivateKey(_pemKey);
        //Sign
        var signedData =
            rsaCryptoServiceProvider?.SignData(Encoding.UTF8.GetBytes(inputData), SHA256.Create());
        return Convert.ToBase64String(signedData ?? Array.Empty<byte>());
    }

    public bool Verify(string dataForSigning, string signedData)
    {
        //Get key buffer from file

        //Decode public key
        using var rsaCryptoServiceProvider = DecodePublicKey(_publicKey);
        //verify
        var dataForSigningByteArray = Encoding.UTF8.GetBytes(dataForSigning);
        var signedDataByteArray = Convert.FromBase64String(signedData);
        return rsaCryptoServiceProvider != null && rsaCryptoServiceProvider.VerifyData(dataForSigningByteArray,
            SHA256.Create(),
            signedDataByteArray);
    }


    public byte[]? DecodePemKey(string instr)
    {
        const string pempubheader = "-----BEGIN PUBLIC KEY-----";
        const string pempubfooter = "-----END PUBLIC KEY-----";
        const string pemprivheader = "-----BEGIN RSA PRIVATE KEY-----";
        const string pemprivfooter = "-----END RSA PRIVATE KEY-----";

        var pemstr = instr.Trim();
        byte[] binkey;

        var sb = new StringBuilder(pemstr);
        var keystr = sb.Replace(pempubheader, "").Replace(pempubfooter, "").Replace(pemprivheader, "")
            .Replace(pemprivfooter, "").ToString().Trim();

        try
        {
            binkey = Convert.FromBase64String(keystr);
        }
        catch (FormatException)
        {
            //if can't b64 decode, data is not valid
            return null;
        }

        return binkey;
    }

    private RSACryptoServiceProvider? DecodePublicKey(byte[] x509Key)
    {
        // encoded OID sequence for  PKCS #1 rsaEncryption szOID_RSA_RSA = "1.2.840.113549.1.1.1"
        byte[] seqOid = {0x30, 0x0D, 0x06, 0x09, 0x2A, 0x86, 0x48, 0x86, 0xF7, 0x0D, 0x01, 0x01, 0x01, 0x05, 0x00};
        var seq = new byte[15];
        // ---------  Set up stream to read the asn.1 encoded SubjectPublicKeyInfo blob  ------
        var mem = new MemoryStream(x509Key);
        var binr = new BinaryReader(mem); //wrap Memory Stream with BinaryReader for easy reading
        byte bt = 0;
        ushort twobytes = 0;

        try
        {
            twobytes = binr.ReadUInt16();
            if (twobytes == 0x8130) //data read as little endian order (actual data order for Sequence is 30 81)
                binr.ReadByte(); //advance 1 byte
            else if (twobytes == 0x8230)
                binr.ReadInt16(); //advance 2 bytes
            else
                return null;

            seq = binr.ReadBytes(15); //read the Sequence OID
            if (!CompareBytearrays(seq, seqOid)) //make sure Sequence for OID is correct
                return null;

            twobytes = binr.ReadUInt16();
            if (twobytes == 0x8103) //data read as little endian order (actual data order for Bit string is 03 81)
                binr.ReadByte(); //advance 1 byte
            else if (twobytes == 0x8203)
                binr.ReadInt16(); //advance 2 bytes
            else
                return null;

            bt = binr.ReadByte();
            if (bt != 0x00) //expect null byte next
                return null;

            twobytes = binr.ReadUInt16();
            if (twobytes == 0x8130) //data read as little endian order (actual data order for Sequence is 30 81)
                binr.ReadByte(); //advance 1 byte
            else if (twobytes == 0x8230)
                binr.ReadInt16(); //advance 2 bytes
            else
                return null;

            twobytes = binr.ReadUInt16();
            byte lowbyte = 0x00;
            byte highbyte = 0x00;

            if (twobytes == 0x8102) //data read as little endian order (actual data order for Integer is 02 81)
            {
                lowbyte = binr.ReadByte(); // read next bytes which is bytes in modulus
            }
            else if (twobytes == 0x8202)
            {
                highbyte = binr.ReadByte(); //advance 2 bytes
                lowbyte = binr.ReadByte();
            }
            else
            {
                return null;
            }

            byte[] modint = {lowbyte, highbyte, 0x00, 0x00}; //reverse byte order since asn.1 key uses big endian order
            var modsize = BitConverter.ToInt32(modint, 0);

            var firstbyte = binr.ReadByte();
            binr.BaseStream.Seek(-1, SeekOrigin.Current);

            if (firstbyte == 0x00)
            {
                //if first byte (highest order) of modulus is zero, don't include it
                binr.ReadByte(); //skip this null byte
                modsize -= 1; //reduce modulus buffer size by 1
            }

            var modulus = binr.ReadBytes(modsize); //read the modulus bytes

            if (binr.ReadByte() != 0x02) //expect an Integer for the exponent data
                return null;
            int expbytes =
                binr.ReadByte(); // should only need one byte for actual exponent data (for all useful values)
            var exponent = binr.ReadBytes(expbytes);


            // ------- create RSACryptoServiceProvider instance and initialize with public key -----
            var RSA = new RSACryptoServiceProvider();
            var RSAKeyInfo = new RSAParameters();
            RSAKeyInfo.Modulus = modulus;
            RSAKeyInfo.Exponent = exponent;
            RSA.ImportParameters(RSAKeyInfo);
            return RSA;
        }
        catch (Exception)
        {
            return null;
        }

        finally
        {
            binr.Close();
        }
    }

    private RSACryptoServiceProvider? DecodeRSAPrivateKey(byte[] privkey)
    {
        byte[] MODULUS, E, D, P, Q, DP, DQ, IQ;

        // ---------  Set up stream to decode the asn.1 encoded RSA private key  ------
        var mem = new MemoryStream(privkey);
        var binr = new BinaryReader(mem); //wrap Memory Stream with BinaryReader for easy reading
        byte bt = 0;
        ushort twobytes = 0;
        var elems = 0;
        try
        {
            twobytes = binr.ReadUInt16();
            if (twobytes == 0x8130) //data read as little endian order (actual data order for Sequence is 30 81)
                binr.ReadByte(); //advance 1 byte
            else if (twobytes == 0x8230)
                binr.ReadInt16(); //advance 2 bytes
            else
                return null;

            twobytes = binr.ReadUInt16();
            if (twobytes != 0x0102) //version number
                return null;
            bt = binr.ReadByte();
            if (bt != 0x00)
                return null;

            //------  all private key components are Integer sequences ----
            elems = GetIntegerSize(binr);
            MODULUS = binr.ReadBytes(elems);

            elems = GetIntegerSize(binr);
            E = binr.ReadBytes(elems);

            elems = GetIntegerSize(binr);
            D = binr.ReadBytes(elems);

            elems = GetIntegerSize(binr);
            P = binr.ReadBytes(elems);

            elems = GetIntegerSize(binr);
            Q = binr.ReadBytes(elems);

            elems = GetIntegerSize(binr);
            DP = binr.ReadBytes(elems);

            elems = GetIntegerSize(binr);
            DQ = binr.ReadBytes(elems);

            elems = GetIntegerSize(binr);
            IQ = binr.ReadBytes(elems);

            //  System.Security.Cryptography.RSA.Create();

            // ------- create RSACryptoServiceProvider instance and initialize with public key -----
            var RSA = new RSACryptoServiceProvider();
            var RSAparams = new RSAParameters();
            RSAparams.Modulus = MODULUS;
            RSAparams.Exponent = E;
            RSAparams.D = D;
            RSAparams.P = P;
            RSAparams.Q = Q;
            RSAparams.DP = DP;
            RSAparams.DQ = DQ;
            RSAparams.InverseQ = IQ;
            RSA.ImportParameters(RSAparams);
            return RSA;
        }
        catch (Exception)
        {
            return null;
        }
        finally
        {
            binr.Close();
        }
    }

    private int GetIntegerSize(BinaryReader binr)
    {
        byte bt = 0;
        byte lowbyte = 0x00;
        byte highbyte = 0x00;
        var count = 0;
        bt = binr.ReadByte();
        if (bt != 0x02) //expect integer
            return 0;
        bt = binr.ReadByte();

        if (bt == 0x81)
        {
            count = binr.ReadByte(); // data size in next byte
        }
        else if (bt == 0x82)
        {
            highbyte = binr.ReadByte(); // data size in next 2 bytes
            lowbyte = binr.ReadByte();
            byte[] modint = {lowbyte, highbyte, 0x00, 0x00};
            count = BitConverter.ToInt32(modint, 0);
        }
        else
        {
            count = bt; // we already have the data size
        }

        while (binr.ReadByte() == 0x00)
            //remove high order zeros in data
            count -= 1;
        binr.BaseStream.Seek(-1, SeekOrigin.Current); //last ReadByte wasn't a removed zero, so back up a byte
        return count;
    }

    private bool CompareBytearrays(byte[] a, byte[] b)
    {
        if (a.Length != b.Length)
            return false;
        var i = 0;
        foreach (var c in a)
        {
            if (c != b[i])
                return false;
            i++;
        }

        return true;
    }
}