using DecoratorPattern.Component;
using DecoratorPattern.ConcreteComponent;
using DecoratorPattern.ConcreteDecorator;
using DecoratorPattern.Decorator;
using System;

namespace DecoratorPattern
{
    internal class Program
    {
        static void Main(string[] args)
        {
            ICar car = new Suzuki();
            CarDecorator carDecorator = new OfferPrice(car);
            Console.WriteLine(String.Format("Make : {0} , Price: {1} ",carDecorator.Make,carDecorator.GetDiscountedPrice()));
        }
    }
}
