using CompositePattern.Component;
using System;
using System.Collections.Generic;
using System.Text;

namespace CompositePattern.Composite
{
    public class Manager : IEmployee
    {
        string name;
        string dept;
        public List<IEmployee> SubOrdinate;
        public Manager(string name, string dept)
        {
            this.dept = dept;
            this.name = name;
            SubOrdinate = new List<IEmployee>();
        }
        public void getDetails(int indent)
        {
            Console.WriteLine(new String('-', indent)+string.Format("name : {0} , Dept : {1} ", this.name, this.dept));
            foreach(IEmployee sub in SubOrdinate)
            {
                sub.getDetails(indent+1);
            }
        }
    }
}
