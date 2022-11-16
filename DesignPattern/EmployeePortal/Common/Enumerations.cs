using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace EmployeePortal.Common
{
    public enum EmployeeType
    {
        [Display(Name = "Permanent Employee")]
        Permanent,
        [Display(Name = "Temporary Employee")]
        Temporary,
        [Display(Name = "Contract Employee")]
        Contract
    }
    public enum JobType
    {
        [Display(Name = "Manager")]
        Manager,
        [Display(Name = "Non Manager")]
        NonManager,
    }
    public enum ComputerTypes
    {
        [Display(Name = "Laptop")]
        Laptop,
        [Display(Name = "Desktop")]
        Desktop
    }
    public enum Brands
    {
        [Display(Name = "Apple")]
        Apple,
        [Display(Name = "Dell")]
        Dell
    }
    public enum Processors
    {
        [Display(Name = "I3")]
        I3,
        [Display(Name = "I5")]
        I5,
        [Display(Name = "I7")]
        I7
    }
    public enum RAM
    {
        [Display(Name = "8GB RAM")]
        GB8,
        [Display(Name = "16GB RAM")]
        GB16,
        [Display(Name = "32GB RAM")]
        GB32
    }
    public enum HDD
    {
        [Display(Name = "500 GB")]
        GB500,
        [Display(Name = "1 TB")]
        TB1,
    }
}
