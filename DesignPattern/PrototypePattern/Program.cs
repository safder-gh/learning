using System;

namespace PrototypePattern
{
    class Program
    {
        static void Main(string[] args)
        {
            Employee Emp = new Employee();

            Console.WriteLine("First Name");
            Emp.FirstName = Console.ReadLine();

            Console.WriteLine("Last Name");
            Emp.LastName = Console.ReadLine();

            Console.WriteLine("House Number");
            Emp.Address.HouseNumber = Console.ReadLine();

            Console.WriteLine("Street Number");
            Emp.Address.StreetNumber = Console.ReadLine();

            Console.WriteLine("Town");
            Emp.Address.Town = Console.ReadLine();

            Console.WriteLine("Shellow Copy or Deep Copy(1/0)");
            Char ch = Console.ReadKey().KeyChar;
            switch (ch)
            {
                case '1':
                    {
                        Employee OtherEmployee = Emp.ShallowCopy();
                        Console.WriteLine("Original object");
                        Console.WriteLine(Emp.ToString());
                        Console.WriteLine("-------------------------------");
                        Console.WriteLine("Clone Object");
                        Console.WriteLine(OtherEmployee.ToString());
                        break;
                    }
                case '0':
                    {

                        Employee OtherEmployee = Emp.DeepCopy();
                        Console.WriteLine("Original object");
                        Console.WriteLine(Emp.ToString());
                        Console.WriteLine("-------------------------------");
                        Console.WriteLine("Clone Object");
                        Console.WriteLine(OtherEmployee.ToString());
                        break;
                    }
                default:
                    {
                        Console.WriteLine("Bad Choice");
                        break;
                    }
            }
        }
    }

}
