using AdapterDesign.Adapter;
using System;

namespace AdapterDesign
{
    internal class Program
    {
        static void Main(string[] args)
        {
            EmployeeManager manager = new EmployeeManager();
            Console.WriteLine(manager.GetAllEmployees());
            IEmployee employeeAdapter = new EmployeeAdapter();
            Console.WriteLine(employeeAdapter.GetAllEmployees());

        }
    }
}
