using System;

namespace PrototypePattern
{
    internal class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Creating Employee");
            Employee employee = new Employee(1,"Safder","Nothing","Bahadurabad",25000);
            employee.Show();
            Console.WriteLine("Creating 2nd Employee");
            Employee employee1 = (Employee)employee.GetClone();
            employee1.Show();
        }
    }
    interface Prototype
    {
        public Prototype GetClone();
    }
    class Employee : Prototype
    {
        private int id;
        private string name, designation, address;
        private double salary;
        public Employee()
        {
            Console.WriteLine("Employee Records of Oracle Carporation");
            Console.WriteLine("-------------------------------");
            Console.WriteLine("Employee Id "+"\t"+" Employee Name "+"\t"+" Employee Salary "+"\t\t"+"Employee Address");

        }
        public Employee(int id, string name, string designation, string address, double salary) : this()
        {
            this.id = id;
            this.name = name;
            this.designation = designation;
            this.address = address;
            this.salary = salary;
        }
        public void Show()
        {
            Console.WriteLine(string.Format("Employee id is {0} Employee Name is {1} employee designation is {2} Employee Address is {3} and salary is {4}", this.id, this.name, this.designation,this.address, this.salary));
        }
        public Prototype GetClone()
        {
            return new Employee(this.id, this.name, this.designation, this.address, this.salary);
        }
    }
}
