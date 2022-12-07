using System;
using System.Runtime.ConstrainedExecution;

namespace AbstractFactory_Bank_
{
    internal class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Bank Name:");
            var BankName = Console.ReadLine();
            Console.WriteLine("Loan Type:");
            var LoanType = Console.ReadLine();
            Console.WriteLine("Loan Rate:");
            double rate = Double.Parse( Console.ReadLine());
            Console.WriteLine("Loan Amount:");
            double amount = Double.Parse(Console.ReadLine());
            Console.WriteLine("Loan years:");
            int years = Int16.Parse(Console.ReadLine());

            AbstractFactory bankFactory = FactoryCreator.GetFactory("Bank");
            Bank Bank = bankFactory.GetBank(BankName);

            AbstractFactory loanFactory = FactoryCreator.GetFactory("Loan");
            Loan loan = loanFactory.GetLoan(LoanType);
            loan.SetInterestRate(rate);
            loan.CalculateLoanPayment(amount, years);
            Console.WriteLine(string.Format("Bank Name is {0} and loan type is {1}", Bank.GetName(), LoanType));
        }
    }
    public interface Bank
    {
        string GetName();
    }

    class HDFC : Bank
    {
        private readonly string _name;
        public HDFC()
        {
            this._name = "HDFC Bank";
        }
        public string GetName()
        {
            return this._name;
        }
    }
    class ICICI : Bank
    {
        private readonly string _name;
        public ICICI()
        {
            this._name = "ICICI Bank";
        }
        public string GetName()
        {
            return this._name;
        }
    }
    class SBI : Bank
    {
        private readonly string _name;
        public SBI()
        {
            this._name = "SBI Bank";
        }
        public string GetName()
        {
            return this._name;
        }
    }

    abstract class Loan
    {
        protected double rate;
        public abstract void SetInterestRate(double rate);
        public void CalculateLoanPayment(double loanamount, int years)
        {
            double EMI;
            int n;
            n = years * 12;
            rate = rate / 1200;
            EMI = ((rate * Math.Pow((1 + rate), n)) / ((Math.Pow((1 + rate), n) - 1)) * loanamount);
            Console.WriteLine("your monthly EMI is " + EMI + " for amount of " + loanamount + " you have borrowed");
        }
    }

    class HomeLoan : Loan
    {
        public override void SetInterestRate(double rate)
        {
            this.rate = rate;
        }
    }
    class BusinessLoan : Loan
    {
        public override void SetInterestRate(double rate)
        {
            this.rate = rate;
        }
    }
    class EducationLoan : Loan
    {
        public override void SetInterestRate(double rate)
        {
            this.rate = rate;
        }
    }

    abstract class AbstractFactory
    {
        public abstract Bank GetBank(string bank);
        public abstract Loan GetLoan(string loan);
    }
    class BankFactory : AbstractFactory
    {
        public override Bank GetBank(string name)
        {
            Bank Bank = null;
            switch (name)
            {
                case "HDFC":
                    {
                        Bank = new HDFC();
                        break;
                    }
                case "ICICI":
                    {
                        Bank = new ICICI();
                        break;
                    }
                default:
                    {
                        Bank = new SBI();

                        break;
                    }
            }
            return Bank;
        }

        public override Loan GetLoan(string loan)
        {
            return null;
        }
    }
    class LoanFactory : AbstractFactory
    {
        public override Bank GetBank(string bank)
        {
            return null;  
        }

        public override Loan GetLoan(string type)
        {
            Loan Loan = null;
            switch (type)
            {
                case "Home":
                    {
                        Loan = new HomeLoan();
                        break;
                    }
                case "Business":
                    {
                        Loan = new BusinessLoan();
                        break;
                    }
                default:
                    {
                        Loan = new EducationLoan();
                        break;
                    }
            }
            return Loan;
        }
    }
    class FactoryCreator
    {
        public static AbstractFactory GetFactory(string choice)
        {
            AbstractFactory factory = null;
            switch (choice)
            {
                case "Bank":
                    {
                        factory = new BankFactory();
                        break;
                    }
                case "Loan":
                    {
                        factory = new LoanFactory();
                        break;
                    }
                default:
                    {
                        factory = new LoanFactory();
                        break;
                    }
            }
            return factory;
        }
    }
}
