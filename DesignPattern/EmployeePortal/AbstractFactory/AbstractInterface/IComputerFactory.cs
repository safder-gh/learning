using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace EmployeePortal.AbstractFactory
{
    public interface IComputerFactory
    {
        IProcessor GetProcessor();
        IBrand GetBrand();
        ISystemType GetSystemType();
    }
}
