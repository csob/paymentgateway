using System;
using System.IO;

namespace CsobGatewayClientExample.Common
{
    public static class Constants
    {
        public static readonly string PrivateKeyFilePath = Path.Combine(Environment.CurrentDirectory, @"Assets", "rsa_A2169rONf8.key");
        public static readonly string PublicKeyFilePath = Path.Combine(Environment.CurrentDirectory, @"Assets", "rsa_A2169rONf8.pub");
        public static readonly string PaymentInitBaseFilePath = Path.Combine(Environment.CurrentDirectory, @"Assets", "payment-init-base.json");
        public static readonly string PaymentOneclickBaseFilePath = Path.Combine(Environment.CurrentDirectory, @"Assets", "payment-oneclick-base.json");
        public const string GatewayUrl = "https://iapi.iplatebnibrana.csob.cz/api/v1.6";
        public const string MerchantId = "A2169rONf8";
    }
}
