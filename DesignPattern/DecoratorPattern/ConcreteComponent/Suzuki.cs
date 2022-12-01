using DecoratorPattern.Component;
using System;
using System.Collections.Generic;
using System.Text;

namespace DecoratorPattern.ConcreteComponent
{
    public sealed class Suzuki : ICar
    {
        public string Make
        {
            get { return "Cultus"; }
        }

        public double GetPrice()
        {
            return 1000000;
        }
    }
}
