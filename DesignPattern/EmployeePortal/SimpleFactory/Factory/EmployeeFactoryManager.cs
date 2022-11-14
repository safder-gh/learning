using EmployeePortal.Common;
using EmployeePortal.SimpleFactory.Managers;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace EmployeePortal.SimpleFactory.Factory
{
    public class EmployeeFactoryManager
    {
        public IEmployeeManager GetManager(EmployeeType employeeType)
        {
            IEmployeeManager employeeManager = null;
            if (employeeType == EmployeeType.Permanent)
            {
                employeeManager = new PermenantEmployeeManager();
            }
            else if (employeeType == EmployeeType.Temporary)
            {
                employeeManager = new ContractEmployeeManager();
            }
            return employeeManager;
        }
    }
}
