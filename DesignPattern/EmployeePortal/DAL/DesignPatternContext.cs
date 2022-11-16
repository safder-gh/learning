using EmployeePortal.Models;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using EmployeePortal.ViewModel;

namespace EmployeePortal.DAL
{
    public class DesignPatternContext:DbContext
    {
        public DesignPatternContext(DbContextOptions<DesignPatternContext> options):base(options)
        {

        }
        public DbSet<Employee> Employees { set; get; }
        public DbSet<EmployeePortal.ViewModel.SystemConfigurationDetails> SystemConfigurationDetails { get; set; }
    }
}
