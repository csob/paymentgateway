using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CsobGatewayClientExample.Common;
using CsobGatewayClientExample.Communication;
using CsobGatewayClientExample.Communication.DataObjects;
using Newtonsoft.Json;

namespace CsobGatewayClientExample
{
    class Program
    {
        private static string paymentId = string.Empty;

        private static readonly Dictionary<int, string> MethodList = new Dictionary<int, string>()
        {
            {1, "echo get" },
            {2, "echo post" },
            {3, "init" },
            {4, "process" },
            {5, "status" },
            {6, "close" },
            {7, "reverse" },
            {8, "refund" },
            {9, "oneclick init" },
            {10, "oneclick start" },
            {11, "oneclick info" },
        };

        static void Main(string[] args)
        {
            var gatewayClient = new GatewayClient()
            {
                MerchantId = Constants.MerchantId
            };
            ChooseMethod(gatewayClient);
        }

        private static void ChooseMethod(GatewayClient gatewayClient, bool error = false)
        {
            Console.Clear();
            Console.WriteLine("Press number representing action:");
            foreach (var keyValue in MethodList)
                Console.WriteLine($"{keyValue.Key}. {keyValue.Value}");

            if (error)
                Console.WriteLine("\nYou must enter id of method in list!");

            Console.WriteLine();
            Console.Write("Select method: ");

            var result = CheckInput(Console.ReadLine());
            if (result == null)
                ChooseMethod(gatewayClient, true);

            Console.WriteLine($"\nSelected method: {result} - {MethodList[result.Value]}");

            Start(gatewayClient, result.Value);

            Console.ReadLine();

            ChooseMethod(gatewayClient);
        }

        private static int? CheckInput(string id)
        {
            int value;
            var isNumeric = int.TryParse(id, out value);

            if (!isNumeric || value < 1 || value > 11)
                return null;

            return value;
        }

        private static async void Start(GatewayClient gatewayClient, int value)
        {
            Console.WriteLine();
            Console.WriteLine("Processing ...");

            ClientResponse response = new ClientResponse();

            switch (value)
            {
                case 1:
                    response = await gatewayClient.CallEchoGet();
                    break;
                case 2:
                    response = await gatewayClient.CallEchoPost();
                    break;
                case 3:
                    response = await gatewayClient.CallInit();
                    var responseObject = JsonConvert.DeserializeObject<PayRes>(response.ResponseValue);
                    paymentId = responseObject.PayId;
                    break;
                case 4:
                    if (!CheckPaymentId())
                        return;
                    response = await gatewayClient.CallProcess(paymentId);
                    break;
                case 5:
                    if (!CheckPaymentId())
                        return;
                    response = await gatewayClient.CallStatus(paymentId);
                    break;
                case 6:
                    if (!CheckPaymentId())
                        return;
                    response = await gatewayClient.CallClose(paymentId);
                    break;
                case 7:
                    if (!CheckPaymentId())
                        return;
                    response = await gatewayClient.CallReverse(paymentId);
                    break;
                case 8:
                    if (!CheckPaymentId())
                        return;
                    response = await gatewayClient.CallRefund(paymentId, 150000);
                    break;
                case 9:
                    if (!CheckPaymentId())
                        return;
                    response = await gatewayClient.CallOneClickInit(paymentId);
                    break;
                case 10:
                    if (!CheckPaymentId())
                        return;
                    response = await gatewayClient.CallOneClickStart(paymentId);
                    break;
                case 11:
                    response = await gatewayClient.CallCustomerInfo("xxx");
                    break;
            }

            Console.WriteLine();

            Console.Write("ResponseCode: ");
            Console.ForegroundColor = (response.ResponseCode.StartsWith("200")|| response.ResponseCode.StartsWith("303")) ? ConsoleColor.Green : ConsoleColor.Red;
            Console.WriteLine(response.ResponseCode);
            Console.ResetColor();

            Console.WriteLine("ResponseValue: " + response.ResponseValue);

            Console.WriteLine();
            Console.WriteLine("Press Enter to continue.");
        }

        private static bool CheckPaymentId()
        {
            if (string.IsNullOrEmpty(paymentId))
            {
                Console.WriteLine("First get PaymentId by calling method 3 - init");
                return false;
            }

            return true;
        }
    }
}
