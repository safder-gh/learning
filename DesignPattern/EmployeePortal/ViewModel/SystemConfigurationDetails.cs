using EmployeePortal.Common;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace EmployeePortal.ViewModel
{
    public class SystemConfigurationDetails
    {
        public SystemConfigurationDetails(Guid Id)
        {
            this.Id = Id;
        }
        [Key]
        public Guid Id { set; get; }
        [Display(Name="Memory Size")]
        [Required]
        public RAM RAM { set; get; }
        [Display(Name = "HDD Size")]
        [Required]
        public HDD HDD { set; get; }
    }
}
