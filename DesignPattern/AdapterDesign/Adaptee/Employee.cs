using System;
using System.Collections.Generic;
using System.Text;
using System.Xml.Serialization;

namespace AdapterDesign.Adaptee
{
    public class Employee
    {
        [XmlAttribute]
        public int Id { get; set; }
        [XmlAttribute]
        public string Name { get; set; }
        public Employee() { }
        public Employee(int Id, string name)
        {
            this.Id = Id;
            Name = name;
        }
    }
}
