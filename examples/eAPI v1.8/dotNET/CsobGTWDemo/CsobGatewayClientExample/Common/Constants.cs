using System;
using System.IO;

namespace CsobGatewayClientExample.Common
{
    public static class Constants
    {
        public static readonly string PrivateKeyFilePath = Path.Combine(Environment.CurrentDirectory, @"Assets", "rsa_A3070d3A3V.key");
        public static readonly string PaymentInitBaseFilePath = Path.Combine(Environment.CurrentDirectory, @"Assets", "payment-init-base.json");
        public static readonly string PaymentOneclickBaseFilePath = Path.Combine(Environment.CurrentDirectory, @"Assets", "payment-oneclick-base.json");
        public const string GatewayUrl = "https://iapi.iplatebnibrana.csob.cz/api/v1.8";
        public const string MerchantId = "A3070d3A3V";
    }
}
