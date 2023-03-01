using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Threading.Tasks;
using viewdata_viewbag_tempdata.Models;
using viewdata_viewbag_tempdata.testing;

namespace viewdata_viewbag_tempdata.Controllers
{
    public class HomeController : Controller
    {
        private readonly ILogger<HomeController> _logger;
        private readonly string my_name = "Safder";
        const string sur_name = "Ghauri";
        public HomeController(ILogger<HomeController> logger)
        {
            ImplementedClass implementedClass = new ImplementedClass();
            AbsClass absClass = new ImplementedClass();
            _logger = logger;
            this.my_name = "Ghauri";
        }

        public IActionResult Index()
        {

            TempData["test"] = "Safder Ghauri";
            //return RedirectToAction("privacy");
            return View();
        }

        public IActionResult Privacy()
        {
            return View();
        }

        [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
        public IActionResult Error()
        {
            return View(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
        }
        public IActionResult xss()
        {
            return View();
        }
    }
}
