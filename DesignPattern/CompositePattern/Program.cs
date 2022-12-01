using CompositePattern.Component;
using CompositePattern.Composite;
using CompositePattern.Leaf;
using System;

namespace CompositePattern
{
    internal class Program
    {
        static void Main(string[] args)
        {
            IEmployee john = new Employee("John", "IT");
            IEmployee tom = new Employee("Tom", "IT");
            IEmployee same = new Employee("Same", "HR");
            IEmployee carl = new Employee("Carl", "HR");
            IEmployee fred = new Employee("Fred", "HR");

            IEmployee james = new Manager("James", "IT") { SubOrdinate = { john, tom } };
            IEmployee philips = new Manager("Philips", "HR") { SubOrdinate = { same,carl,fred } };

            IEmployee bob = new Manager("Bob", "IT") { SubOrdinate = { james,philips} };
            bob.getDetails(1);
        }
    }
}
