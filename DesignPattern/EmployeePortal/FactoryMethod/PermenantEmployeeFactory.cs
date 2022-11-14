using EmployeePortal.Models;
using EmployeePortal.SimpleFactory.Managers;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace EmployeePortal.FactoryMethod
{
    public class PermenantEmployeeFactory : BaseEmployeeFactory
    {
        public PermenantEmployeeFactory(Employee _employee) : base(_employee)
        {
        }

        public override IEmployeeManager Create()
        {
            PermenantEmployeeManager permenantEmployeeManager = new PermenantEmployeeManager();
            this.employee.HouseAllowance = permenantEmployeeManager.GetHouseAllowance();
            this.employee.MedicalAllowance = permenantEmployeeManager.GetMedicalAllowance();
            return permenantEmployeeManager;
        }
    }
}
