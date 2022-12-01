using CompositePattern.Component;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading;

namespace CompositePattern.Leaf
{
    public class Employee : IEmployee
    {
        string name;
        string dept;
        public Employee(string name,string dept)
        {
            this.dept = dept;
            this.name = name;
        }
        public void getDetails(int indent)
        {
            Console.WriteLine(new String('-', indent)+string.Format("name : {0} , Dept : {1} ",this.name,this.dept) );
        }
    }
}
