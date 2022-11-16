using EmployeePortal.Common;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace EmployeePortal.AbstractFactory
{
    public class MAC : IBrand
    {
        public string GetBrand()
        {
            return Brands.Apple.ToString();
        }
    }
}
