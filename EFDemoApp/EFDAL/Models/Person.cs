﻿using System;
using System.Collections.Generic;
using System.Text;

namespace EFDAL.Models
{
    public class Person
    {
        public int Id { get; set; }
        public string FirstName { get; set; }
        public string MiddleName { get; set; }
        public string LastName { get; set; }
        public int Age { get; set; }
        public List<Address> Addresses { get; set; } = new List<Address>();
        public List<Email> Emails { get; set; } = new List<Email>();
    }
}
