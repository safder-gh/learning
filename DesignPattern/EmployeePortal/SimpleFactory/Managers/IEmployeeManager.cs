using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace EmployeePortal.SimpleFactory.Managers
{
    public interface IEmployeeManager
    {
        public double GetBonus();
        public double GetHourlyPay();
    }
}
