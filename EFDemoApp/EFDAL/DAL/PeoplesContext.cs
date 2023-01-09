using EFDAL.Models;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Text;

namespace EFDAL.DAL
{
    public class PeoplesContext:DbContext
    {
        public PeoplesContext(DbContextOptions options):base(options) {}
        public DbSet<Person> Persons { get; set; }
        public DbSet<Email> Emails { get; set; }
        public DbSet<Address> Addreses { get; set; }
    }
}
