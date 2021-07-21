%====================================================================================
% parcheggio description   
%====================================================================================
context(ctxbasicrobot, "localhost",  "TCP", "8082").
 qactor( outsonar, ctxbasicrobot, "outSonar").
  qactor( weightsensor, ctxbasicrobot, "it.unibo.weightsensor.Weightsensor").
  qactor( thermometer, ctxbasicrobot, "it.unibo.thermometer.Thermometer").
  qactor( fan, ctxbasicrobot, "it.unibo.fan.Fan").
  qactor( basicrobot, ctxbasicrobot, "it.unibo.basicrobot.Basicrobot").
  qactor( parkmanagerservice, ctxbasicrobot, "it.unibo.parkmanagerservice.Parkmanagerservice").
  qactor( cliente, ctxbasicrobot, "it.unibo.cliente.Cliente").
