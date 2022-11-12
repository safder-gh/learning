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
        Temporary
    }
}
