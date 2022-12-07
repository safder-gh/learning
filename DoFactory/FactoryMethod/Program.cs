using System;

namespace FactoryMethod
{
    internal class Program
    {
        static void Main(string[] args)
        {
            PlanFactory planFactory = new PlanFactory();
            Plan plan = planFactory.GetPlan("DOMESTIC");
            plan.GetRate();
            plan.CalculateBill(15);
        }
    }
    public class PlanFactory
    {
        public Plan GetPlan(string PlanType)
        {
            Plan plan;
            switch (PlanType)
            {
                case "DOMESTIC":
                    {
                        plan = new DometicPlan();
                        break;
                    }
                case "COMMERCIAL":
                    {
                        plan = new CommercialPlan();
                        break;
                    }
                case "INSTITUTIONAL":
                    {
                        plan = new InstitutionalPlan();
                        break;
                    }
                default:
                    {
                        throw new ArgumentException(); 
                    }
            }
            return plan;
        }
    }

    public abstract class Plan
    {
        protected double rate;
        public abstract void GetRate();
        public void CalculateBill(int Unites)
        {
            Console.WriteLine(Unites*rate);
        }
    }
    public class DometicPlan : Plan
    {
        public override void GetRate()
        {
            rate = 3.50;
        }
    }
    public class CommercialPlan : Plan
    {
        public override void GetRate()
        {
            rate = 7.50;
        }
    }
    public class InstitutionalPlan : Plan
    {
        public override void GetRate()
        {
            rate = 5.50;
        }
    }
}
