using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace EmployeePortal.AbstractFactory
{
    public class EmployeeSystemManager
    {
         IComputerFactory ComputerFactory { get; }
        public EmployeeSystemManager(IComputerFactory ComputerFactory)
        {
            this.ComputerFactory = ComputerFactory;
        }
        public string GetsystemDetails()
        {
           
            IBrand brand = ComputerFactory.GetBrand();
            IProcessor processor = ComputerFactory.GetProcessor();
            ISystemType systemType = ComputerFactory.GetSystemType();
            string details = string.Format("{0} {1} {2}", brand.GetBrand(),processor.GetProcessor(),systemType.GetSystemType()) ;
            return details;
        }
       
    }
}
