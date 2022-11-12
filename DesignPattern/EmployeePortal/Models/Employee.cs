using EmployeePortal.Common;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace EmployeePortal.Models
{
    [Table("employee")]
    public class Employee : BaseModel
    {
        [Required]
        [Display(Name ="First Name")]
        [Column("first_name", Order = 1)]
        public string FirstName { set; get; }
       
        [Column("last_name", Order = 2)]
        [Display(Name = "Last Name")]
        public string LastName { set; get; }
        [Column("jd", Order = 3)]
        [Display(Name = "Job Description")]
        public string JD { set; get; }
        [Column("employee_type", Order = 4)]
        [Required]
        [Display(Name = "Employee Type")]
        public EmployeeType EmployeeType { set; get; }
        [Column("hourly_pay", Order = 5)]
        [Display(Name = "Hourly Pay")]
        public double HourlyPay { set; get; }
        [Column("bonus", Order = 6)]
        [Display(Name = "Bonus")]
        public double Bonus { set; get; }
    }
}
