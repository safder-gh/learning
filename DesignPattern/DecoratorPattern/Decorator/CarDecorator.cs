using DecoratorPattern.Component;
using System;
using System.Collections.Generic;
using System.Text;

namespace DecoratorPattern.Decorator
{
    public abstract class CarDecorator : ICar
    {
        private ICar car;
        public CarDecorator(ICar car)
        {
            this.car = car;
        }
        public string Make { get { return car.Make; } }


        public double GetPrice()
        {
            return car.GetPrice();
        }
        public abstract double GetDiscountedPrice();
    }
}
