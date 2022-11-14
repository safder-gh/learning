using EmployeePortal.Common;
using EmployeePortal.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace EmployeePortal.FactoryMethod
{
    public class EmployeeManagerFactory
    {
        public BaseEmployeeFactory CreateFactory(Employee employee)
        {
            BaseEmployeeFactory baseEmployeeFactory = null;
            switch (employee.EmployeeType)
            {
                case EmployeeType.Permanent:
                    {
                        baseEmployeeFactory = new PermenantEmployeeFactory(employee);
                        break;
                    }
                case EmployeeType.Contract:
                    {
                        baseEmployeeFactory = new ContractEmployeeFactory(employee);
                        break;
                    } 
            }
            return baseEmployeeFactory;
        }
    }
}
