using System;
using System.Collections.Generic;

namespace Builder
{
    internal class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Buiding Vehicle!");
            VehicleCreator vehicleCreator = new VehicleCreator(new HeroBuilder());
            vehicleCreator.CreateVehicle();
            Vehicle vehicle = vehicleCreator.GetVehicle();
            vehicle.ShowInfo();

            vehicleCreator = new VehicleCreator(new HondaBuilder());
            vehicleCreator.CreateVehicle();
            Vehicle vehicle2 = vehicleCreator.GetVehicle();
            vehicle2.ShowInfo();


        }
    }
    public interface IVehicleBuilder
    {
        void SetModel();
        void SetEngine();
        void SetTransmission();
        void SetBody();
        void SetAccessories();
        Vehicle GetVehicle();
    }
    public class HeroBuilder : IVehicleBuilder
    {
        Vehicle Vehicle = new Vehicle();
        public Vehicle GetVehicle()
        {
            return Vehicle;
        }

        public void SetAccessories()
        {
            Vehicle.Accessories.Add("Seat Cover");
            Vehicle.Accessories.Add("Rear Mirror");
        }

        public void SetBody()
        {
            Vehicle.Body = "Plastic";
        }

        public void SetEngine()
        {
            Vehicle.Engine = "4 Stroke";
        }

        public void SetModel()
        {
            Vehicle.Model = "Hero";
        }

        public void SetTransmission()
        {
            Vehicle.Transmission = "120 km/hr";
        }
    }
    public class HondaBuilder : IVehicleBuilder
    {
        Vehicle Vehicle = new Vehicle();
        public Vehicle GetVehicle()
        {
            return Vehicle;
        }

        public void SetAccessories()
        {
            Vehicle.Accessories.Add("Seat Cover");
            Vehicle.Accessories.Add("Rear Mirror");
            Vehicle.Accessories.Add("Helmet");
        }

        public void SetBody()
        {
            Vehicle.Body = "Plastic";
        }

        public void SetEngine()
        {
            Vehicle.Engine = "4 Stroke";
        }

        public void SetModel()
        {
            Vehicle.Model = "Honda";
        }

        public void SetTransmission()
        {
            Vehicle.Transmission = "125 km/hr";
        }
    }
    public class SuzukiBuilder : IVehicleBuilder
    {
        Vehicle Vehicle = new Vehicle();
        public Vehicle GetVehicle()
        {
            return Vehicle;
        }

        public void SetAccessories()
        {
            Vehicle.Accessories.Add("Seat Cover");
            Vehicle.Accessories.Add("Rear Mirror");
            Vehicle.Accessories.Add("Helmet");
        }

        public void SetBody()
        {
            Vehicle.Body = "Plastic";
        }

        public void SetEngine()
        {
            Vehicle.Engine = "4 Stroke";
        }

        public void SetModel()
        {
            Vehicle.Model = "Suzuki";
        }

        public void SetTransmission()
        {
            Vehicle.Transmission = "125 km/hr";
        }
    }
    public class Vehicle
    {
        public string Model { get; set; }
        public string Engine { get; set; }
        public string Transmission { get; set; }
        public string Body { get; set; }    
        public List<string> Accessories { get; set; }
        public Vehicle()
        {
            Accessories = new List<string>();
        }
        public void ShowInfo()
        {
            Console.WriteLine("Model:"+this.Model);
            Console.WriteLine("Engine:" + this.Engine);
            Console.WriteLine("Body:" + this.Body);
            Console.WriteLine("Transmission:" + this.Transmission);
            Console.WriteLine("Accessories");
            foreach(var item in this.Accessories)
            {
                Console.WriteLine(item);
            }
        }
    }
    public class VehicleCreator
    {
        private readonly IVehicleBuilder vehicleBuilder;
        public VehicleCreator(IVehicleBuilder vehicleBuilder)
        {
            this.vehicleBuilder = vehicleBuilder;
        }
        public void CreateVehicle()
        {
            vehicleBuilder.SetAccessories();
            vehicleBuilder.SetEngine();
            vehicleBuilder.SetTransmission();
            vehicleBuilder.SetBody();
            vehicleBuilder.SetModel();                
        }
        public Vehicle GetVehicle()
        {
            return vehicleBuilder.GetVehicle();
        }
    }
}
