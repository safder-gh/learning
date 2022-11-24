using System;
using System.Collections.Generic;
using System.Text;

namespace PrototypePattern
{
    public class Employee
    {
        public Employee()
        {
            this.Address = new Address();
            this.Id = new Random().Next();
        }
        public int Id { get; set; }
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public Address Address { get; set; }

        public Employee ShallowCopy()
        {
            return (Employee)this.MemberwiseClone();
        }

        public Employee DeepCopy()
        {
            Employee other = (Employee)this.MemberwiseClone();
            other.Address = new Address(this.Id,this.Address.HouseNumber,
                this.Address.StreetNumber, this.Address.Town);
            return other;
        }

        public override string ToString()
        {
            StringBuilder sb = new StringBuilder();
            sb.Append(string.Format("Employee Hash Code : {0}\n", this.GetHashCode()));
            sb.Append(string.Format("Address Hash Code : {0}\n", this.Address.GetHashCode()));
            return sb.ToString();
        }
    }
}
