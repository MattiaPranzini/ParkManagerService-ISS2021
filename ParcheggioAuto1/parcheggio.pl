%====================================================================================
% parcheggio description   
%====================================================================================
context(ctxparcheggio, "localhost",  "TCP", "8082").
 qactor( outsonar, ctxparcheggio, "outSonar").
  qactor( weightsensor, ctxparcheggio, "it.unibo.weightsensor.Weightsensor").
  qactor( parkmanagerservice, ctxparcheggio, "it.unibo.parkmanagerservice.Parkmanagerservice").
  qactor( cliente, ctxparcheggio, "it.unibo.cliente.Cliente").
