using EmployeePortal.Common;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace EmployeePortal.Builder.Product
{
    public class ComputerSystem
    {
        public RAM RAM { set; get; }
        public HDD HDD { set; get; }
        public KEYBOARD KEYBOARD { set; get; }
        public MOUSE MOUSE { set; get; }
        public TOUCHSCREEN TOUCHSCREEN { set; get; }
    }
}
