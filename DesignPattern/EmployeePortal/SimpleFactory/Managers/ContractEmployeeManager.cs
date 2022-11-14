using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace EmployeePortal.SimpleFactory.Managers
{
    public class ContractEmployeeManager : IEmployeeManager
    {
        public double GetBonus()
        {
            return 12.0;
        }

        public double GetHourlyPay()
        {
            return 15.0;
        }
        public double GetMedicalAllowance()
        {
            return 150;
        }
    }
}
