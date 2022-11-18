using EmployeePortal.Common;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace EmployeePortal.ViewModel
{
    public class SystemConfigurationDetails
    {
        public SystemConfigurationDetails()
        {

        }
        public SystemConfigurationDetails(Guid Id)
        {
            this.Id = Id;
        }
        public string BuidSystem()
        {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.Append(string.Format("RAM Size {0} ", EnumHelper<RAM>.GetDisplayValue(RAM)));
            stringBuilder.Append(string.Format("HDD Size {0} ", EnumHelper<HDD>.GetDisplayValue(HDD)));
            return stringBuilder.ToString();
        }
        [Key]
        public Guid Id { set; get; }
        [Display(Name="Memory Size")]
        [Required]
        public RAM RAM { set; get; }
        [Display(Name = "HDD Size")]
        [Required]
        public HDD HDD { set; get; }
        [Display(Name = "Touch Screen")]
        [Required]
        public TOUCHSCREEN TOUCHSCREEN { set; get; }
        [Display(Name = "Keyboard")]
        [Required]
        public KEYBOARD KEYBOARD { set; get; }
        [Display(Name = "Mouse")]
        [Required]
        public MOUSE MOUSE { set; get; }
    }
}
