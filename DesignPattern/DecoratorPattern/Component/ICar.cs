using System;
using System.Collections.Generic;
using System.Text;

namespace DecoratorPattern.Component
{
    public interface ICar
    {
        string Make { get; }
        double GetPrice();
    }
}
