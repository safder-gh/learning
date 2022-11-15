using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace EmployeePortal.AbstractFactory
{
    public class DellFactory : IComputerFactory
    {
        public IBrand GetBrand()
        {
            return new Dell();
        }

        public IProcessor GetProcessor()
        {
            return new I5();
        }

        public virtual ISystemType GetSystemType()
        {
            return new Desktop();
        }
    }
    public class DellLaptopFactory : DellFactory
    {
        public override ISystemType GetSystemType()
        {
            return new Laptop();
        }
    }
}
