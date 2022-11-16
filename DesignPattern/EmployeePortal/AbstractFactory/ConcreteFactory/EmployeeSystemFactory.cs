using EmployeePortal.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace EmployeePortal.AbstractFactory
{
    public class EmployeeSystemFactory
    {
        public IComputerFactory Create(Employee Employee)
        {
            IComputerFactory computerFactory = null;
            switch (Employee.EmployeeType)
            {
                case Common.EmployeeType.Permanent:
                    {
                        switch (Employee.JobType)
                        {
                            case Common.JobType.Manager:
                                {
                                    computerFactory = new MACLaptopFactory();
                                    break;
                                }
                            default:
                                {
                                    computerFactory = new MACFactory();
                                    break;
                                }
                        }
                        break;
                    }
                case Common.EmployeeType.Contract:
                    {
                        switch (Employee.JobType)
                        {
                            case Common.JobType.Manager:
                                {
                                    computerFactory = new DellLaptopFactory();
                                    break;
                                }
                            default:
                                {
                                    computerFactory = new DellFactory();
                                    break;
                                }
                        }
                        break;
                    }
            }
            return computerFactory;
        }
    }
}
