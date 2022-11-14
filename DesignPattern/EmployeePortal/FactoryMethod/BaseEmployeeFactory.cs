using EmployeePortal.Models;
using EmployeePortal.SimpleFactory.Managers;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace EmployeePortal.FactoryMethod
{
    public abstract class BaseEmployeeFactory
    {
        protected Employee employee;
        public BaseEmployeeFactory(Employee _employee) => employee = _employee;
        public Employee ApplySalary()
        {
            IEmployeeManager employeeManager = Create();
            employee.Bonus = employeeManager.GetBonus();
            employee.HourlyPay = employeeManager.GetHourlyPay();
            return employee;
        }
        public abstract IEmployeeManager Create();
    }
}
