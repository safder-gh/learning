using EmployeePortal.Common;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace EmployeePortal.AbstractFactory
{
    public class Dell : IBrand
    {
        string IBrand.GetBrand()
        {
            return Brands.Dell.ToString();
        }
    }
}
