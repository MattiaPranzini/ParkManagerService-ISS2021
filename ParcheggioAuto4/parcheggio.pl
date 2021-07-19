%====================================================================================
% parcheggio description   
%====================================================================================
context(ctxparcheggio, "localhost",  "TCP", "8082").
 qactor( outsonar, ctxparcheggio, "outSonar").
  qactor( weightsensor, ctxparcheggio, "it.unibo.weightsensor.Weightsensor").
  qactor( thermometer, ctxparcheggio, "it.unibo.thermometer.Thermometer").
  qactor( fan, ctxparcheggio, "it.unibo.fan.Fan").
  qactor( managerservice, ctxparcheggio, "it.unibo.managerservice.Managerservice").
  qactor( parkmanagerservice, ctxparcheggio, "it.unibo.parkmanagerservice.Parkmanagerservice").
  qactor( cliente, ctxparcheggio, "it.unibo.cliente.Cliente").
