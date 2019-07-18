using System;
using System.Collections.Generic;
using System.Text;

namespace HFTR.Common.Models
{
    public class InstrumentData
    {
        public double Profit
        {
            get
            {
                return ContractMultiplier * (CurrentValue - PositionCost) - Fee - OrderFee;
            }
        }
        public double CurrentValue
        {
            get
            {
                return CurrentPrice * (CurrentLongPosition - CurrentShortPosition);
            }
        }
        public double CurrentPrice { get; set; } = 0;
        public int NetPosition
        {
            get
            {
                return CurrentLongPosition - CurrentShortPosition;
            }
        }
        public string InstrumentId { get; set; } = "";
        public int CurrentLongPosition { get; set; } = 0;
        public int CurrentShortPosition { get; set; } = 0;
        public double PositionCost { get; set; } = 0;
        public double Fee { get; set; } = 0;
        public double OrderFee { get; set; } = 0;
        public int OrderInsertNum { get; set; } = 0;
        public int OrderCancelNum { get; set; } = 0;
        public int OrderVolume { get; set; } = 0;
        public int Volume { get; set; } = 0;
        public int TradeVolume { get; set; } = 0;

        public int ContractMultiplier { get; set; } = 0;
        public int InitLongPosition { get; set; } = 0;
        public int InitShortPosition { get; set; } = 0;
        public double PreSettlementPrice { get; set; } = 0;
        public bool IsMarketDataInitialized { get; set; } = false;
    }

    public class OrderData
    {
        public int OrderSysId { get; set; } = 0;
        public string InstrumentId { get; set; }
        public string Direction { get; set; }
        public string OffsetFlag { get; set; }
        public double Price { get; set; } = 0;
        public double TotalVolume { get; set; } = 0;
        public double TradedVolume { get; set; } = 0;
        public string InsertTime { get; set; } = "";

        public OrderData(Socket.OrderRtn rtn)
        {
            OrderSysId = rtn.OrderSysId;
            InstrumentId = rtn.InstrumentId;
            Direction = rtn.Direction.ToString();
            OffsetFlag = rtn.OffsetFlag.ToString();
            Price = rtn.Price;
            TotalVolume = rtn.TotalVolume;
            TradedVolume = rtn.TradedVolume;
            InsertTime = rtn.InsertTime;
        }

    }

    public class MonitorData
    {
        public Dictionary<string, InstrumentData> Instruments { get; set; } = new Dictionary<string, InstrumentData>();
        public Dictionary<string, Dictionary<int, OrderData>> Orders { get; set; } = new Dictionary<string, Dictionary<int, OrderData>>();
//        public double TotalFunds { get; set; } = 0;

        public double IFProfit
        {
            get
            {
                double sum = 0;
                foreach (var instrument in Instruments)
                {
                    if (instrument.Key.StartsWith("IF"))
                        sum += instrument.Value.Profit;
                }
                return sum;
            }
        }

        public double IFPercentage
        {
            get
            {
                return TotalFunds == 0 ? 0 : IFProfit / TotalFunds;
            }
        }

        public double IHProfit
        {
            get
            {
                double sum = 0;
                foreach (var instrument in Instruments)
                {
                    if (instrument.Key.StartsWith("IH"))
                        sum += instrument.Value.Profit;
                }
                return sum;
            }
        }

        public double IHPercentage
        {
            get
            {
                return TotalFunds == 0 ? 0 : IHProfit / TotalFunds;
            }
        }

        public double ICProfit
        {
            get
            {
                double sum = 0;
                foreach (var instrument in Instruments)
                {
                    if (instrument.Key.StartsWith("IC"))
                        sum += instrument.Value.Profit;
                }
                return sum;
            }
        }

        public double ICPercentage
        {
            get
            {
                return TotalFunds == 0 ? 0 : ICProfit / TotalFunds;
            }
        }


        public double TotalProfit
        {
            get
            {
                double sum = 0;
                foreach (var instrument in Instruments)
                    sum += instrument.Value.Profit;
                return sum;
            }
        }

        public double TotalFee
        {
            get
            {
                double sum = 0;
                foreach (var instrument in Instruments)
                    sum += instrument.Value.Fee;
                return sum;
            }
        }

        public double TotalOrderFee
        {
            get
            {
                double sum = 0;
                foreach (var instrument in Instruments)
                    sum += instrument.Value.OrderFee;
                return sum;
            }
        }
    }
}

