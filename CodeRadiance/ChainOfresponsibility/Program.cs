using System;
using System.Collections.Generic;

namespace ChainOfresponsibility
{
    class Program
    {
        static void Main(string[] args)
        {
            Person person = new Person()
            {
                Age = 60,
                Name = "Safder Ghauri",
                Salary = 60000
            };
            Request request = new Request() { Data = person };
            MaxAgeHandler maxAgeHandler = new MaxAgeHandler();
            MaxNameLengthHandler maxNameLengthHandler = new MaxNameLengthHandler();
            MaxSalaryHandler maxSalaryHandler = new MaxSalaryHandler();

            maxAgeHandler.SetNextHandler(maxNameLengthHandler);
            maxNameLengthHandler.SetNextHandler(maxSalaryHandler);

            maxAgeHandler.Process(request);
            foreach(var messag in request.ValidationMessages)
            {
                Console.WriteLine(messag);
            }


            Console.WriteLine("Hello World!");
        }
    }
    interface IHandler
    {
        void SetNextHandler(IHandler handler);
        void Process(Request request);
    }
    class Basehandler : IHandler
    {
        protected IHandler _NextHandler;
        public Basehandler()
        {
            _NextHandler = null;
        }
        public  virtual void Process(Request request)
        {
            throw new NotImplementedException();
        }

        public void SetNextHandler(IHandler handler)
        {
            _NextHandler = handler;
        }
    }
    class MaxAgeHandler : Basehandler
    {
        public override void Process(Request request)
        {
            if(request.Data is Person person)
            {
                if (person.Age > 55) request.ValidationMessages.Add("Invalid Age");
                if (_NextHandler != null) _NextHandler.Process(request);
            }
            else
            {
                throw new Exception("Invalid Message Data");
            }
        }
    }
    class MaxNameLengthHandler : Basehandler
    {
        public override void Process(Request request)
        {
            if (request.Data is Person person)
            {
                if (person.Name.Length > 10) request.ValidationMessages.Add("Invalid Name");
                if (_NextHandler != null) _NextHandler.Process(request);
            }
            else
            {
                throw new Exception("Invalid Message Data");
            }
        }
    }
    class MaxSalaryHandler : Basehandler
    {
        public override void Process(Request request)
        {
            if (request.Data is Person person)
            {
                if (person.Name.Length < 50000) request.ValidationMessages.Add("Invalid Salary");
                if (_NextHandler != null) _NextHandler.Process(request);
            }
            else
            {
                throw new Exception("Invalid Message Data");
            }
        }
    }
    class Request
    {
        public object Data { set; get; }
        public List<string> ValidationMessages;
        public Request()
        {
            ValidationMessages = new List<string>();
        }
    }
    class Person
    {
        public string Name { get; set; }
        public int Age { get; set; }
        public double Salary { get; set; }
    }
}
