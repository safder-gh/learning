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
            stringBuilder.Append(string.Format("HDD Size {0}", HDD.HasValue ? EnumHelper<HDD>.GetDisplayValue((HDD)HDD):string.Empty));
            stringBuilder.Append(string.Format("RAM Size {0}", RAM.HasValue ? EnumHelper<RAM>.GetDisplayValue((RAM)RAM) : string.Empty));
            stringBuilder.Append(string.Format("Mouse Type {0}", MOUSE.HasValue ? EnumHelper<MOUSE>.GetDisplayValue((MOUSE)MOUSE) : string.Empty));
            stringBuilder.Append(string.Format("Touchscreen {0}", TOUCHSCREEN.HasValue ? EnumHelper<TOUCHSCREEN>.GetDisplayValue((TOUCHSCREEN)TOUCHSCREEN) : "Not Applicable"));
            stringBuilder.Append(string.Format("Keyboard Type {0}", KEYBOARD.HasValue ? EnumHelper<KEYBOARD>.GetDisplayValue((KEYBOARD)KEYBOARD) : "Not Applicable"));
            return stringBuilder.ToString();
        }
        [Key]
        public Guid Id { set; get; }
        [Display(Name = "Memory Size")]
        [Required]
        public RAM? RAM { set; get; } = null;
        [Display(Name = "HDD Size")]
        [Required]
        public HDD? HDD { set; get; } = null;
        [Display(Name = "Touch Screen")]
        [Required]
        public TOUCHSCREEN? TOUCHSCREEN { set; get; } = null;
        [Display(Name = "Keyboard")]
        [Required]
        public KEYBOARD? KEYBOARD { set; get; } = null;
        [Display(Name = "Mouse")]
        [Required]
        public MOUSE? MOUSE { set; get; } = null;
    }
}
