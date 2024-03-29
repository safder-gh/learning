﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace EmployeePortal.AbstractFactory
{
    public class MACFactory : IComputerFactory
    {
        public IBrand GetBrand()
        {
            return new MAC();
        }

        public IProcessor GetProcessor()
        {
            return new I7();
        }

        public virtual ISystemType GetSystemType()
        {
            return new Desktop();
        }
    }
    public class MACLaptopFactory : MACFactory
    {
        public override ISystemType GetSystemType()
        {
            return new Laptop();
        }
    }
}
