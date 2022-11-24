using System;
using System.Collections.Generic;
using System.Text;

namespace PrototypePattern
{
    public class Address
    {
        public Address()
        {
            this.Id = new Random().Next();
        }
        public Address(int EmployeeId,string HouseNumber,string StreetNumber, string Town)
        {
            this.Id = new Random().Next();
            this.EmployeeId = EmployeeId;
            this.HouseNumber = HouseNumber;
            this.StreetNumber = StreetNumber;
            this.Town = Town;
        }
        public int Id { get; set; }
        public int EmployeeId { get; set; }
        public string HouseNumber { get; set; }
        public string StreetNumber { get; set; }
        public string Town { get; set; }
    }
}
