using EmployeePortal.Models;
using EmployeePortal.SimpleFactory.Managers;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace EmployeePortal.FactoryMethod
{
    public class ContractEmployeeFactory : BaseEmployeeFactory
    {
        public ContractEmployeeFactory(Employee _employee) : base(_employee)
        {
        }

        public override IEmployeeManager Create()
        {
            ContractEmployeeManager contractEmployeeManager = new ContractEmployeeManager();
            this.employee.HouseAllowance = contractEmployeeManager.GetMedicalAllowance();
            return contractEmployeeManager;
        }
    }
}
