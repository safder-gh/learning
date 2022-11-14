using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace EmployeePortal.SimpleFactory.Managers
{
    public class PermenantEmployeeManager : IEmployeeManager
    {
        public double GetBonus()
        {
            return 15.0;
        }

        public double GetHourlyPay()
        {
            return 20.0;
        }
        public double GetHouseAllowance()
        {
            return 100;
        }
        public double GetMedicalAllowance()
        {
            return 300;
        }
    }
}
