using DecoratorPattern.Component;
using DecoratorPattern.Decorator;
using System;
using System.Collections.Generic;
using System.Text;

namespace DecoratorPattern.ConcreteDecorator
{
    public class OfferPrice : CarDecorator
    {
        public OfferPrice(ICar car):base(car){}
        public override double GetDiscountedPrice()
        {
           return .8 * base.GetPrice();
        }
    }
}
