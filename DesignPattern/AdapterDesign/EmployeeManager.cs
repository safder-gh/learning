using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using System.Threading;
using System.Xml;
using System.Xml.Serialization;
using AdapterDesign.Adaptee;

namespace AdapterDesign
{
    public class EmployeeManager
    {
        public List<Employee> EmployeeList;
        public EmployeeManager()
        {
            EmployeeList = new List<Employee>();
            EmployeeList.Add(new Employee(1,"Employee 1"));
            EmployeeList.Add(new Employee(2, "Employee 2"));
            EmployeeList.Add(new Employee(3, "Employee 3"));
            EmployeeList.Add(new Employee(4, "Employee 4"));
            EmployeeList.Add(new Employee(5, "Employee 5"));
        }
        public virtual string GetAllEmployees()
        {
            var emptyNamspaces = new XmlSerializerNamespaces(new[] {XmlQualifiedName.Empty });
            var serialize = new XmlSerializer(EmployeeList.GetType());
            var setting = new XmlWriterSettings();
            setting.Indent = true;
            using(var stream = new StringWriter())
                using(var writer = XmlWriter.Create(stream, setting))
            {
                serialize.Serialize(writer, EmployeeList,emptyNamspaces);
                return stream.ToString();
            } 
        }

    }
}
