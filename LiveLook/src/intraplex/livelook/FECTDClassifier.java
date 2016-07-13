package intraplex.livelook;


/*
=== Run information ===

Scheme:weka.classifiers.trees.J48 -C 0.25 -M 2
Relation:     lossmodel-dataset-4
Instances:    58480
Attributes:   6
              Loss Rate
              Burst Size
              Gap Density
              Burst Density
              Ratio
              UseFEC
Test mode:split 80.0% train, remainder test

=== Classifier model (full training set) ===

J48 pruned tree
------------------

Burst Density <= 0.223786
|   Burst Density <= 0.201818
|   |   Loss Rate <= 0.043211: TRUE (11590.0/3.0)
|   |   Loss Rate > 0.043211
|   |   |   Burst Density <= 0.182375: TRUE (1349.0/8.0)
|   |   |   Burst Density > 0.182375
|   |   |   |   Gap Density <= 0.03885
|   |   |   |   |   Ratio <= 0.86865
|   |   |   |   |   |   Burst Size <= 72: TRUE (208.0/1.0)
|   |   |   |   |   |   Burst Size > 72
|   |   |   |   |   |   |   Loss Rate <= 0.144401: TRUE (11.0)
|   |   |   |   |   |   |   Loss Rate > 0.144401: FALSE (2.0)
|   |   |   |   |   Ratio > 0.86865
|   |   |   |   |   |   Burst Size <= 50: TRUE (4.0)
|   |   |   |   |   |   Burst Size > 50
|   |   |   |   |   |   |   Gap Density <= 0.038101: TRUE (3.0/1.0)
|   |   |   |   |   |   |   Gap Density > 0.038101: FALSE (5.0)
|   |   |   |   Gap Density > 0.03885
|   |   |   |   |   Burst Density <= 0.194572
|   |   |   |   |   |   Gap Density <= 0.04081: TRUE (46.0/8.0)
|   |   |   |   |   |   Gap Density > 0.04081
|   |   |   |   |   |   |   Burst Size <= 50: TRUE (6.0/1.0)
|   |   |   |   |   |   |   Burst Size > 50
|   |   |   |   |   |   |   |   Loss Rate <= 0.048091: TRUE (2.0)
|   |   |   |   |   |   |   |   Loss Rate > 0.048091
|   |   |   |   |   |   |   |   |   Burst Density <= 0.185731
|   |   |   |   |   |   |   |   |   |   Gap Density <= 0.04894
|   |   |   |   |   |   |   |   |   |   |   Loss Rate <= 0.049591: FALSE (4.0/1.0)
|   |   |   |   |   |   |   |   |   |   |   Loss Rate > 0.049591: TRUE (3.0)
|   |   |   |   |   |   |   |   |   |   Gap Density > 0.04894: FALSE (3.0)
|   |   |   |   |   |   |   |   |   Burst Density > 0.185731: FALSE (13.0)
|   |   |   |   |   Burst Density > 0.194572: FALSE (37.0/10.0)
|   Burst Density > 0.201818
|   |   Loss Rate <= 0.037379
|   |   |   Ratio <= 0.562953
|   |   |   |   Loss Rate <= 0.025063
|   |   |   |   |   Ratio <= 0.154907
|   |   |   |   |   |   Loss Rate <= 0.019057: TRUE (14.0)
|   |   |   |   |   |   Loss Rate > 0.019057: FALSE (4.0)
|   |   |   |   |   Ratio > 0.154907: TRUE (314.0/1.0)
|   |   |   |   Loss Rate > 0.025063
|   |   |   |   |   Ratio <= 0.23511: FALSE (22.0)
|   |   |   |   |   Ratio > 0.23511
|   |   |   |   |   |   Burst Density <= 0.209625: TRUE (64.0/1.0)
|   |   |   |   |   |   Burst Density > 0.209625
|   |   |   |   |   |   |   Burst Size <= 53: TRUE (25.0)
|   |   |   |   |   |   |   Burst Size > 53
|   |   |   |   |   |   |   |   Loss Rate <= 0.030467
|   |   |   |   |   |   |   |   |   Gap Density <= 0.008335
|   |   |   |   |   |   |   |   |   |   Burst Size <= 60: TRUE (2.0)
|   |   |   |   |   |   |   |   |   |   Burst Size > 60: FALSE (3.0)
|   |   |   |   |   |   |   |   |   Gap Density > 0.008335: TRUE (20.0/3.0)
|   |   |   |   |   |   |   |   Loss Rate > 0.030467
|   |   |   |   |   |   |   |   |   Burst Density <= 0.212506
|   |   |   |   |   |   |   |   |   |   Gap Density <= 0.012726: FALSE (4.0)
|   |   |   |   |   |   |   |   |   |   Gap Density > 0.012726: TRUE (6.0/2.0)
|   |   |   |   |   |   |   |   |   Burst Density > 0.212506: FALSE (32.0)
|   |   |   Ratio > 0.562953: TRUE (1035.0/1.0)
|   |   Loss Rate > 0.037379
|   |   |   Burst Size <= 47
|   |   |   |   Gap Density <= 0.039211
|   |   |   |   |   Burst Size <= 42: TRUE (73.0)
|   |   |   |   |   Burst Size > 42
|   |   |   |   |   |   Burst Density <= 0.223102
|   |   |   |   |   |   |   Gap Density <= 0.036695
|   |   |   |   |   |   |   |   Burst Density <= 0.220239: TRUE (35.0)
|   |   |   |   |   |   |   |   Burst Density > 0.220239
|   |   |   |   |   |   |   |   |   Loss Rate <= 0.043195: TRUE (4.0)
|   |   |   |   |   |   |   |   |   Loss Rate > 0.043195: FALSE (2.0)
|   |   |   |   |   |   |   Gap Density > 0.036695
|   |   |   |   |   |   |   |   Burst Density <= 0.208367: TRUE (5.0)
|   |   |   |   |   |   |   |   Burst Density > 0.208367: FALSE (6.0/2.0)
|   |   |   |   |   |   Burst Density > 0.223102: FALSE (3.0)
|   |   |   |   Gap Density > 0.039211: FALSE (10.0)
|   |   |   Burst Size > 47
|   |   |   |   Burst Density <= 0.215106
|   |   |   |   |   Gap Density <= 0.037019
|   |   |   |   |   |   Gap Density <= 0.017338
|   |   |   |   |   |   |   Burst Density <= 0.203375: TRUE (10.0)
|   |   |   |   |   |   |   Burst Density > 0.203375: FALSE (58.0/12.0)
|   |   |   |   |   |   Gap Density > 0.017338
|   |   |   |   |   |   |   Ratio <= 0.858071
|   |   |   |   |   |   |   |   Burst Size <= 79
|   |   |   |   |   |   |   |   |   Burst Density <= 0.209226: TRUE (95.0/1.0)
|   |   |   |   |   |   |   |   |   Burst Density > 0.209226
|   |   |   |   |   |   |   |   |   |   Gap Density <= 0.022651
|   |   |   |   |   |   |   |   |   |   |   Burst Size <= 62: TRUE (4.0/1.0)
|   |   |   |   |   |   |   |   |   |   |   Burst Size > 62: FALSE (4.0)
|   |   |   |   |   |   |   |   |   |   Gap Density > 0.022651: TRUE (54.0/5.0)
|   |   |   |   |   |   |   |   Burst Size > 79
|   |   |   |   |   |   |   |   |   Burst Density <= 0.210251: TRUE (4.0)
|   |   |   |   |   |   |   |   |   Burst Density > 0.210251: FALSE (8.0)
|   |   |   |   |   |   |   Ratio > 0.858071
|   |   |   |   |   |   |   |   Gap Density <= 0.035436
|   |   |   |   |   |   |   |   |   Burst Density <= 0.206102: TRUE (12.0)
|   |   |   |   |   |   |   |   |   Burst Density > 0.206102
|   |   |   |   |   |   |   |   |   |   Burst Size <= 51: TRUE (5.0)
|   |   |   |   |   |   |   |   |   |   Burst Size > 51
|   |   |   |   |   |   |   |   |   |   |   Ratio <= 0.870874: TRUE (2.0)
|   |   |   |   |   |   |   |   |   |   |   Ratio > 0.870874: FALSE (11.0/2.0)
|   |   |   |   |   |   |   |   Gap Density > 0.035436: FALSE (11.0/1.0)
|   |   |   |   |   Gap Density > 0.037019
|   |   |   |   |   |   Ratio <= 0.873605
|   |   |   |   |   |   |   Ratio <= 0.808488
|   |   |   |   |   |   |   |   Loss Rate <= 0.074245: TRUE (5.0)
|   |   |   |   |   |   |   |   Loss Rate > 0.074245: FALSE (4.0)
|   |   |   |   |   |   |   Ratio > 0.808488: FALSE (62.0/3.0)
|   |   |   |   |   |   Ratio > 0.873605
|   |   |   |   |   |   |   Gap Density <= 0.037616: FALSE (4.0/1.0)
|   |   |   |   |   |   |   Gap Density > 0.037616: TRUE (6.0/1.0)
|   |   |   |   Burst Density > 0.215106
|   |   |   |   |   Ratio <= 0.396148: FALSE (52.0)
|   |   |   |   |   Ratio > 0.396148
|   |   |   |   |   |   Loss Rate <= 0.042173
|   |   |   |   |   |   |   Burst Density <= 0.2207: TRUE (39.0/17.0)
|   |   |   |   |   |   |   Burst Density > 0.2207: FALSE (12.0/1.0)
|   |   |   |   |   |   Loss Rate > 0.042173: FALSE (100.0/10.0)
Burst Density > 0.223786
|   Ratio <= 0.854341
|   |   Loss Rate <= 0.009983
|   |   |   Burst Density <= 0.375293
|   |   |   |   Ratio <= 0.48314
|   |   |   |   |   Loss Rate <= 0.006224: TRUE (404.0/6.0)
|   |   |   |   |   Loss Rate > 0.006224
|   |   |   |   |   |   Burst Density <= 0.309994
|   |   |   |   |   |   |   Ratio <= 0.271084
|   |   |   |   |   |   |   |   Burst Size <= 73: TRUE (29.0)
|   |   |   |   |   |   |   |   Burst Size > 73
|   |   |   |   |   |   |   |   |   Burst Density <= 0.276546: TRUE (11.0)
|   |   |   |   |   |   |   |   |   Burst Density > 0.276546
|   |   |   |   |   |   |   |   |   |   Loss Rate <= 0.007768: TRUE (5.0)
|   |   |   |   |   |   |   |   |   |   Loss Rate > 0.007768
|   |   |   |   |   |   |   |   |   |   |   Burst Size <= 91: FALSE (8.0)
|   |   |   |   |   |   |   |   |   |   |   Burst Size > 91
|   |   |   |   |   |   |   |   |   |   |   |   Burst Size <= 105: TRUE (2.0)
|   |   |   |   |   |   |   |   |   |   |   |   Burst Size > 105: FALSE (2.0)
|   |   |   |   |   |   |   Ratio > 0.271084: TRUE (87.0)
|   |   |   |   |   |   Burst Density > 0.309994
|   |   |   |   |   |   |   Burst Size <= 49: TRUE (18.0/2.0)
|   |   |   |   |   |   |   Burst Size > 49
|   |   |   |   |   |   |   |   Ratio <= 0.242193: FALSE (46.0)
|   |   |   |   |   |   |   |   Ratio > 0.242193
|   |   |   |   |   |   |   |   |   Loss Rate <= 0.007263: TRUE (13.0/1.0)
|   |   |   |   |   |   |   |   |   Loss Rate > 0.007263
|   |   |   |   |   |   |   |   |   |   Burst Density <= 0.336691
|   |   |   |   |   |   |   |   |   |   |   Ratio <= 0.393255: FALSE (6.0)
|   |   |   |   |   |   |   |   |   |   |   Ratio > 0.393255
|   |   |   |   |   |   |   |   |   |   |   |   Gap Density <= 0.003764: TRUE (3.0)
|   |   |   |   |   |   |   |   |   |   |   |   Gap Density > 0.003764: FALSE (3.0/1.0)
|   |   |   |   |   |   |   |   |   |   Burst Density > 0.336691: FALSE (21.0)
|   |   |   |   Ratio > 0.48314: TRUE (774.0/1.0)
|   |   |   Burst Density > 0.375293
|   |   |   |   Loss Rate <= 0.004719
|   |   |   |   |   Ratio <= 0.479094
|   |   |   |   |   |   Loss Rate <= 0.002809
|   |   |   |   |   |   |   Loss Rate <= 0.002113: TRUE (410.0)
|   |   |   |   |   |   |   Loss Rate > 0.002113
|   |   |   |   |   |   |   |   Ratio <= 0.177757
|   |   |   |   |   |   |   |   |   Burst Density <= 0.620438: TRUE (31.0)
|   |   |   |   |   |   |   |   |   Burst Density > 0.620438
|   |   |   |   |   |   |   |   |   |   Loss Rate <= 0.002419
|   |   |   |   |   |   |   |   |   |   |   Gap Density <= 0.000141
|   |   |   |   |   |   |   |   |   |   |   |   Burst Size <= 60: TRUE (2.0)
|   |   |   |   |   |   |   |   |   |   |   |   Burst Size > 60: FALSE (5.0)
|   |   |   |   |   |   |   |   |   |   |   Gap Density > 0.000141: TRUE (14.0/2.0)
|   |   |   |   |   |   |   |   |   |   Loss Rate > 0.002419
|   |   |   |   |   |   |   |   |   |   |   Burst Size <= 26: TRUE (2.0)
|   |   |   |   |   |   |   |   |   |   |   Burst Size > 26: FALSE (26.0/1.0)
|   |   |   |   |   |   |   |   Ratio > 0.177757: TRUE (149.0/2.0)
|   |   |   |   |   |   Loss Rate > 0.002809
|   |   |   |   |   |   |   Ratio <= 0.356893
|   |   |   |   |   |   |   |   Burst Density <= 0.537062
|   |   |   |   |   |   |   |   |   Loss Rate <= 0.003795
|   |   |   |   |   |   |   |   |   |   Gap Density <= 0.000409
|   |   |   |   |   |   |   |   |   |   |   Burst Density <= 0.488139
|   |   |   |   |   |   |   |   |   |   |   |   Burst Size <= 71: TRUE (6.0)
|   |   |   |   |   |   |   |   |   |   |   |   Burst Size > 71
|   |   |   |   |   |   |   |   |   |   |   |   |   Ratio <= 0.09392: FALSE (5.0/1.0)
|   |   |   |   |   |   |   |   |   |   |   |   |   Ratio > 0.09392: TRUE (3.0)
|   |   |   |   |   |   |   |   |   |   |   Burst Density > 0.488139: FALSE (5.0)
|   |   |   |   |   |   |   |   |   |   Gap Density > 0.000409: TRUE (35.0/1.0)
|   |   |   |   |   |   |   |   |   Loss Rate > 0.003795
|   |   |   |   |   |   |   |   |   |   Burst Density <= 0.415861
|   |   |   |   |   |   |   |   |   |   |   Gap Density <= 0.000489: FALSE (3.0/1.0)
|   |   |   |   |   |   |   |   |   |   |   Gap Density > 0.000489: TRUE (5.0)
|   |   |   |   |   |   |   |   |   |   Burst Density > 0.415861
|   |   |   |   |   |   |   |   |   |   |   Gap Density <= 0.001113: FALSE (41.0/2.0)
|   |   |   |   |   |   |   |   |   |   |   Gap Density > 0.001113
|   |   |   |   |   |   |   |   |   |   |   |   Burst Size <= 52: TRUE (4.0)
|   |   |   |   |   |   |   |   |   |   |   |   Burst Size > 52: FALSE (2.0)
|   |   |   |   |   |   |   |   Burst Density > 0.537062
|   |   |   |   |   |   |   |   |   Loss Rate <= 0.003051
|   |   |   |   |   |   |   |   |   |   Ratio <= 0.244268
|   |   |   |   |   |   |   |   |   |   |   Burst Size <= 33: TRUE (3.0/1.0)
|   |   |   |   |   |   |   |   |   |   |   Burst Size > 33: FALSE (28.0)
|   |   |   |   |   |   |   |   |   |   Ratio > 0.244268
|   |   |   |   |   |   |   |   |   |   |   Burst Density <= 0.900337: TRUE (10.0)
|   |   |   |   |   |   |   |   |   |   |   Burst Density > 0.900337: FALSE (2.0)
|   |   |   |   |   |   |   |   |   Loss Rate > 0.003051: FALSE (267.0/3.0)
|   |   |   |   |   |   |   Ratio > 0.356893
|   |   |   |   |   |   |   |   Loss Rate <= 0.003992
|   |   |   |   |   |   |   |   |   Loss Rate <= 0.003461: TRUE (60.0)
|   |   |   |   |   |   |   |   |   Loss Rate > 0.003461
|   |   |   |   |   |   |   |   |   |   Gap Density <= 0.001439
|   |   |   |   |   |   |   |   |   |   |   Burst Density <= 0.629373: TRUE (8.0)
|   |   |   |   |   |   |   |   |   |   |   Burst Density > 0.629373: FALSE (10.0/1.0)
|   |   |   |   |   |   |   |   |   |   Gap Density > 0.001439: TRUE (31.0/1.0)
|   |   |   |   |   |   |   |   Loss Rate > 0.003992
|   |   |   |   |   |   |   |   |   Burst Density <= 0.530114: TRUE (18.0/1.0)
|   |   |   |   |   |   |   |   |   Burst Density > 0.530114
|   |   |   |   |   |   |   |   |   |   Burst Size <= 59
|   |   |   |   |   |   |   |   |   |   |   Burst Density <= 0.791286: TRUE (11.0/3.0)
|   |   |   |   |   |   |   |   |   |   |   Burst Density > 0.791286: FALSE (16.0)
|   |   |   |   |   |   |   |   |   |   Burst Size > 59: FALSE (32.0)
|   |   |   |   |   Ratio > 0.479094
|   |   |   |   |   |   Loss Rate <= 0.004052: TRUE (915.0)
|   |   |   |   |   |   Loss Rate > 0.004052
|   |   |   |   |   |   |   Ratio <= 0.539445
|   |   |   |   |   |   |   |   Burst Density <= 0.724692: TRUE (18.0)
|   |   |   |   |   |   |   |   Burst Density > 0.724692
|   |   |   |   |   |   |   |   |   Loss Rate <= 0.004332: TRUE (6.0)
|   |   |   |   |   |   |   |   |   Loss Rate > 0.004332: FALSE (9.0/1.0)
|   |   |   |   |   |   |   Ratio > 0.539445: TRUE (160.0/1.0)
|   |   |   |   Loss Rate > 0.004719
|   |   |   |   |   Ratio <= 0.586308
|   |   |   |   |   |   Burst Density <= 0.595737
|   |   |   |   |   |   |   Ratio <= 0.361053
|   |   |   |   |   |   |   |   Burst Size <= 29
|   |   |   |   |   |   |   |   |   Loss Rate <= 0.00637
|   |   |   |   |   |   |   |   |   |   Gap Density <= 0.001449: FALSE (3.0)
|   |   |   |   |   |   |   |   |   |   Gap Density > 0.001449: TRUE (4.0)
|   |   |   |   |   |   |   |   |   Loss Rate > 0.00637: FALSE (16.0)
|   |   |   |   |   |   |   |   Burst Size > 29: FALSE (389.0/1.0)
|   |   |   |   |   |   |   Ratio > 0.361053
|   |   |   |   |   |   |   |   Loss Rate <= 0.006482
|   |   |   |   |   |   |   |   |   Ratio <= 0.501565
|   |   |   |   |   |   |   |   |   |   Burst Size <= 70
|   |   |   |   |   |   |   |   |   |   |   Burst Density <= 0.464767: TRUE (22.0)
|   |   |   |   |   |   |   |   |   |   |   Burst Density > 0.464767
|   |   |   |   |   |   |   |   |   |   |   |   Burst Size <= 35
|   |   |   |   |   |   |   |   |   |   |   |   |   Loss Rate <= 0.005801: TRUE (8.0)
|   |   |   |   |   |   |   |   |   |   |   |   |   Loss Rate > 0.005801: FALSE (3.0/1.0)
|   |   |   |   |   |   |   |   |   |   |   |   Burst Size > 35
|   |   |   |   |   |   |   |   |   |   |   |   |   Burst Density <= 0.512556
|   |   |   |   |   |   |   |   |   |   |   |   |   |   Burst Density <= 0.495871: FALSE (5.0/1.0)
|   |   |   |   |   |   |   |   |   |   |   |   |   |   Burst Density > 0.495871: TRUE (2.0)
|   |   |   |   |   |   |   |   |   |   |   |   |   Burst Density > 0.512556: FALSE (8.0)
|   |   |   |   |   |   |   |   |   |   Burst Size > 70
|   |   |   |   |   |   |   |   |   |   |   Loss Rate <= 0.005109
|   |   |   |   |   |   |   |   |   |   |   |   Gap Density <= 0.001835: FALSE (2.0)
|   |   |   |   |   |   |   |   |   |   |   |   Gap Density > 0.001835: TRUE (4.0)
|   |   |   |   |   |   |   |   |   |   |   Loss Rate > 0.005109: FALSE (24.0/1.0)
|   |   |   |   |   |   |   |   |   Ratio > 0.501565: TRUE (42.0/2.0)
|   |   |   |   |   |   |   |   Loss Rate > 0.006482
|   |   |   |   |   |   |   |   |   Burst Size <= 39
|   |   |   |   |   |   |   |   |   |   Burst Density <= 0.40562: TRUE (11.0)
|   |   |   |   |   |   |   |   |   |   Burst Density > 0.40562
|   |   |   |   |   |   |   |   |   |   |   Ratio <= 0.527551
|   |   |   |   |   |   |   |   |   |   |   |   Loss Rate <= 0.007023
|   |   |   |   |   |   |   |   |   |   |   |   |   Burst Density <= 0.484256: TRUE (4.0)
|   |   |   |   |   |   |   |   |   |   |   |   |   Burst Density > 0.484256: FALSE (3.0/1.0)
|   |   |   |   |   |   |   |   |   |   |   |   Loss Rate > 0.007023: FALSE (33.0/1.0)
|   |   |   |   |   |   |   |   |   |   |   Ratio > 0.527551
|   |   |   |   |   |   |   |   |   |   |   |   Gap Density <= 0.004899: TRUE (13.0/1.0)
|   |   |   |   |   |   |   |   |   |   |   |   Gap Density > 0.004899: FALSE (2.0)
|   |   |   |   |   |   |   |   |   Burst Size > 39
|   |   |   |   |   |   |   |   |   |   Burst Density <= 0.405329
|   |   |   |   |   |   |   |   |   |   |   Ratio <= 0.498884: FALSE (14.0)
|   |   |   |   |   |   |   |   |   |   |   Ratio > 0.498884: TRUE (4.0)
|   |   |   |   |   |   |   |   |   |   Burst Density > 0.405329: FALSE (126.0)
|   |   |   |   |   |   Burst Density > 0.595737
|   |   |   |   |   |   |   Burst Size <= 15
|   |   |   |   |   |   |   |   Ratio <= 0.452433: FALSE (15.0)
|   |   |   |   |   |   |   |   Ratio > 0.452433
|   |   |   |   |   |   |   |   |   Loss Rate <= 0.007996
|   |   |   |   |   |   |   |   |   |   Burst Size <= 14
|   |   |   |   |   |   |   |   |   |   |   Ratio <= 0.516613: FALSE (3.0)
|   |   |   |   |   |   |   |   |   |   |   Ratio > 0.516613: TRUE (2.0)
|   |   |   |   |   |   |   |   |   |   Burst Size > 14: TRUE (4.0)
|   |   |   |   |   |   |   |   |   Loss Rate > 0.007996: FALSE (4.0)
|   |   |   |   |   |   |   Burst Size > 15: FALSE (1340.0/6.0)
|   |   |   |   |   Ratio > 0.586308
|   |   |   |   |   |   Ratio <= 0.750031
|   |   |   |   |   |   |   Loss Rate <= 0.006372
|   |   |   |   |   |   |   |   Ratio <= 0.657681
|   |   |   |   |   |   |   |   |   Burst Density <= 0.78213
|   |   |   |   |   |   |   |   |   |   Burst Density <= 0.61787: TRUE (36.0)
|   |   |   |   |   |   |   |   |   |   Burst Density > 0.61787
|   |   |   |   |   |   |   |   |   |   |   Loss Rate <= 0.005968: TRUE (25.0/2.0)
|   |   |   |   |   |   |   |   |   |   |   Loss Rate > 0.005968
|   |   |   |   |   |   |   |   |   |   |   |   Loss Rate <= 0.006267: FALSE (4.0)
|   |   |   |   |   |   |   |   |   |   |   |   Loss Rate > 0.006267: TRUE (2.0)
|   |   |   |   |   |   |   |   |   Burst Density > 0.78213
|   |   |   |   |   |   |   |   |   |   Loss Rate <= 0.00526: TRUE (7.0)
|   |   |   |   |   |   |   |   |   |   Loss Rate > 0.00526
|   |   |   |   |   |   |   |   |   |   |   Ratio <= 0.637702: FALSE (12.0)
|   |   |   |   |   |   |   |   |   |   |   Ratio > 0.637702
|   |   |   |   |   |   |   |   |   |   |   |   Loss Rate <= 0.00594: TRUE (4.0/1.0)
|   |   |   |   |   |   |   |   |   |   |   |   Loss Rate > 0.00594: FALSE (4.0)
|   |   |   |   |   |   |   |   Ratio > 0.657681: TRUE (137.0)
|   |   |   |   |   |   |   Loss Rate > 0.006372
|   |   |   |   |   |   |   |   Burst Density <= 0.589766
|   |   |   |   |   |   |   |   |   Burst Size <= 28: TRUE (27.0)
|   |   |   |   |   |   |   |   |   Burst Size > 28
|   |   |   |   |   |   |   |   |   |   Ratio <= 0.690626
|   |   |   |   |   |   |   |   |   |   |   Loss Rate <= 0.008042
|   |   |   |   |   |   |   |   |   |   |   |   Ratio <= 0.635542
|   |   |   |   |   |   |   |   |   |   |   |   |   Burst Density <= 0.487669: TRUE (7.0/1.0)
|   |   |   |   |   |   |   |   |   |   |   |   |   Burst Density > 0.487669
|   |   |   |   |   |   |   |   |   |   |   |   |   |   Loss Rate <= 0.006688: TRUE (3.0/1.0)
|   |   |   |   |   |   |   |   |   |   |   |   |   |   Loss Rate > 0.006688: FALSE (6.0)
|   |   |   |   |   |   |   |   |   |   |   |   Ratio > 0.635542: TRUE (18.0)
|   |   |   |   |   |   |   |   |   |   |   Loss Rate > 0.008042
|   |   |   |   |   |   |   |   |   |   |   |   Burst Density <= 0.46692
|   |   |   |   |   |   |   |   |   |   |   |   |   Gap Density <= 0.006069
|   |   |   |   |   |   |   |   |   |   |   |   |   |   Ratio <= 0.61382: FALSE (4.0)
|   |   |   |   |   |   |   |   |   |   |   |   |   |   Ratio > 0.61382: TRUE (9.0)
|   |   |   |   |   |   |   |   |   |   |   |   |   Gap Density > 0.006069: FALSE (4.0)
|   |   |   |   |   |   |   |   |   |   |   |   Burst Density > 0.46692: FALSE (34.0)
|   |   |   |   |   |   |   |   |   |   Ratio > 0.690626
|   |   |   |   |   |   |   |   |   |   |   Gap Density <= 0.006883: TRUE (31.0)
|   |   |   |   |   |   |   |   |   |   |   Gap Density > 0.006883
|   |   |   |   |   |   |   |   |   |   |   |   Loss Rate <= 0.009492: FALSE (2.0)
|   |   |   |   |   |   |   |   |   |   |   |   Loss Rate > 0.009492: TRUE (2.0)
|   |   |   |   |   |   |   |   Burst Density > 0.589766
|   |   |   |   |   |   |   |   |   Ratio <= 0.698367
|   |   |   |   |   |   |   |   |   |   Burst Size <= 24
|   |   |   |   |   |   |   |   |   |   |   Gap Density <= 0.004655: TRUE (9.0/1.0)
|   |   |   |   |   |   |   |   |   |   |   Gap Density > 0.004655: FALSE (23.0/4.0)
|   |   |   |   |   |   |   |   |   |   Burst Size > 24: FALSE (168.0/2.0)
|   |   |   |   |   |   |   |   |   Ratio > 0.698367
|   |   |   |   |   |   |   |   |   |   Loss Rate <= 0.007877
|   |   |   |   |   |   |   |   |   |   |   Ratio <= 0.704508
|   |   |   |   |   |   |   |   |   |   |   |   Loss Rate <= 0.007229: TRUE (3.0)
|   |   |   |   |   |   |   |   |   |   |   |   Loss Rate > 0.007229: FALSE (2.0)
|   |   |   |   |   |   |   |   |   |   |   Ratio > 0.704508: TRUE (29.0)
|   |   |   |   |   |   |   |   |   |   Loss Rate > 0.007877
|   |   |   |   |   |   |   |   |   |   |   Burst Size <= 39
|   |   |   |   |   |   |   |   |   |   |   |   Burst Size <= 24: TRUE (6.0/1.0)
|   |   |   |   |   |   |   |   |   |   |   |   Burst Size > 24
|   |   |   |   |   |   |   |   |   |   |   |   |   Loss Rate <= 0.00816: TRUE (2.0)
|   |   |   |   |   |   |   |   |   |   |   |   |   Loss Rate > 0.00816: FALSE (7.0)
|   |   |   |   |   |   |   |   |   |   |   Burst Size > 39: FALSE (45.0)
|   |   |   |   |   |   Ratio > 0.750031
|   |   |   |   |   |   |   Loss Rate <= 0.008696: TRUE (316.0)
|   |   |   |   |   |   |   Loss Rate > 0.008696
|   |   |   |   |   |   |   |   Ratio <= 0.79853
|   |   |   |   |   |   |   |   |   Burst Density <= 0.678277
|   |   |   |   |   |   |   |   |   |   Ratio <= 0.754185
|   |   |   |   |   |   |   |   |   |   |   Ratio <= 0.752044: TRUE (2.0)
|   |   |   |   |   |   |   |   |   |   |   Ratio > 0.752044: FALSE (2.0)
|   |   |   |   |   |   |   |   |   |   Ratio > 0.754185: TRUE (24.0)
|   |   |   |   |   |   |   |   |   Burst Density > 0.678277
|   |   |   |   |   |   |   |   |   |   Ratio <= 0.769024: FALSE (9.0)
|   |   |   |   |   |   |   |   |   |   Ratio > 0.769024
|   |   |   |   |   |   |   |   |   |   |   Burst Size <= 65: TRUE (12.0)
|   |   |   |   |   |   |   |   |   |   |   Burst Size > 65
|   |   |   |   |   |   |   |   |   |   |   |   Gap Density <= 0.007205: TRUE (2.0)
|   |   |   |   |   |   |   |   |   |   |   |   Gap Density > 0.007205: FALSE (6.0)
|   |   |   |   |   |   |   |   Ratio > 0.79853: TRUE (60.0)
|   |   Loss Rate > 0.009983
|   |   |   Burst Density <= 0.313473
|   |   |   |   Loss Rate <= 0.024829
|   |   |   |   |   Ratio <= 0.465321
|   |   |   |   |   |   Loss Rate <= 0.016553
|   |   |   |   |   |   |   Burst Density <= 0.255758
|   |   |   |   |   |   |   |   Ratio <= 0.186787
|   |   |   |   |   |   |   |   |   Gap Density <= 0.002446: TRUE (19.0/1.0)
|   |   |   |   |   |   |   |   |   Gap Density > 0.002446: FALSE (2.0)
|   |   |   |   |   |   |   |   Ratio > 0.186787: TRUE (87.0)
|   |   |   |   |   |   |   Burst Density > 0.255758
|   |   |   |   |   |   |   |   Ratio <= 0.260942
|   |   |   |   |   |   |   |   |   Loss Rate <= 0.01104
|   |   |   |   |   |   |   |   |   |   Burst Size <= 60: TRUE (2.0)
|   |   |   |   |   |   |   |   |   |   Burst Size > 60
|   |   |   |   |   |   |   |   |   |   |   Burst Density <= 0.265731: TRUE (2.0)
|   |   |   |   |   |   |   |   |   |   |   Burst Density > 0.265731: FALSE (7.0)
|   |   |   |   |   |   |   |   |   Loss Rate > 0.01104: FALSE (70.0/2.0)
|   |   |   |   |   |   |   |   Ratio > 0.260942
|   |   |   |   |   |   |   |   |   Burst Size <= 70
|   |   |   |   |   |   |   |   |   |   Ratio <= 0.309245
|   |   |   |   |   |   |   |   |   |   |   Loss Rate <= 0.014164: TRUE (10.0/1.0)
|   |   |   |   |   |   |   |   |   |   |   Loss Rate > 0.014164: FALSE (7.0/1.0)
|   |   |   |   |   |   |   |   |   |   Ratio > 0.309245: TRUE (40.0/1.0)
|   |   |   |   |   |   |   |   |   Burst Size > 70
|   |   |   |   |   |   |   |   |   |   Loss Rate <= 0.011741: TRUE (10.0)
|   |   |   |   |   |   |   |   |   |   Loss Rate > 0.011741
|   |   |   |   |   |   |   |   |   |   |   Burst Density <= 0.272064
|   |   |   |   |   |   |   |   |   |   |   |   Loss Rate <= 0.014262: TRUE (6.0/1.0)
|   |   |   |   |   |   |   |   |   |   |   |   Loss Rate > 0.014262: FALSE (4.0)
|   |   |   |   |   |   |   |   |   |   |   Burst Density > 0.272064: FALSE (25.0)
|   |   |   |   |   |   Loss Rate > 0.016553
|   |   |   |   |   |   |   Burst Density <= 0.240971
|   |   |   |   |   |   |   |   Burst Size <= 57: TRUE (21.0)
|   |   |   |   |   |   |   |   Burst Size > 57
|   |   |   |   |   |   |   |   |   Loss Rate <= 0.020962
|   |   |   |   |   |   |   |   |   |   Gap Density <= 0.004249: FALSE (7.0)
|   |   |   |   |   |   |   |   |   |   Gap Density > 0.004249
|   |   |   |   |   |   |   |   |   |   |   Burst Density <= 0.231605: TRUE (12.0)
|   |   |   |   |   |   |   |   |   |   |   Burst Density > 0.231605
|   |   |   |   |   |   |   |   |   |   |   |   Ratio <= 0.333579: FALSE (4.0/1.0)
|   |   |   |   |   |   |   |   |   |   |   |   Ratio > 0.333579: TRUE (3.0)
|   |   |   |   |   |   |   |   |   Loss Rate > 0.020962
|   |   |   |   |   |   |   |   |   |   Gap Density <= 0.008173: FALSE (27.0)
|   |   |   |   |   |   |   |   |   |   Gap Density > 0.008173
|   |   |   |   |   |   |   |   |   |   |   Burst Size <= 73: TRUE (2.0)
|   |   |   |   |   |   |   |   |   |   |   Burst Size > 73: FALSE (2.0)
|   |   |   |   |   |   |   Burst Density > 0.240971: FALSE (272.0/7.0)
|   |   |   |   |   Ratio > 0.465321
|   |   |   |   |   |   Burst Density <= 0.260921
|   |   |   |   |   |   |   Ratio <= 0.595001
|   |   |   |   |   |   |   |   Loss Rate <= 0.018714: TRUE (89.0)
|   |   |   |   |   |   |   |   Loss Rate > 0.018714
|   |   |   |   |   |   |   |   |   Burst Size <= 66: TRUE (21.0)
|   |   |   |   |   |   |   |   |   Burst Size > 66
|   |   |   |   |   |   |   |   |   |   Burst Density <= 0.241368: TRUE (9.0)
|   |   |   |   |   |   |   |   |   |   Burst Density > 0.241368
|   |   |   |   |   |   |   |   |   |   |   Ratio <= 0.552151: FALSE (6.0)
|   |   |   |   |   |   |   |   |   |   |   Ratio > 0.552151: TRUE (6.0/1.0)
|   |   |   |   |   |   |   Ratio > 0.595001: TRUE (266.0)
|   |   |   |   |   |   Burst Density > 0.260921
|   |   |   |   |   |   |   Ratio <= 0.607619
|   |   |   |   |   |   |   |   Loss Rate <= 0.015527
|   |   |   |   |   |   |   |   |   Burst Density <= 0.304299: TRUE (48.0/2.0)
|   |   |   |   |   |   |   |   |   Burst Density > 0.304299
|   |   |   |   |   |   |   |   |   |   Loss Rate <= 0.011732: TRUE (4.0)
|   |   |   |   |   |   |   |   |   |   Loss Rate > 0.011732
|   |   |   |   |   |   |   |   |   |   |   Burst Size <= 69: TRUE (3.0/1.0)
|   |   |   |   |   |   |   |   |   |   |   Burst Size > 69: FALSE (5.0)
|   |   |   |   |   |   |   |   Loss Rate > 0.015527
|   |   |   |   |   |   |   |   |   Burst Size <= 43: TRUE (21.0/3.0)
|   |   |   |   |   |   |   |   |   Burst Size > 43
|   |   |   |   |   |   |   |   |   |   Loss Rate <= 0.017075
|   |   |   |   |   |   |   |   |   |   |   Burst Size <= 89: TRUE (7.0/1.0)
|   |   |   |   |   |   |   |   |   |   |   Burst Size > 89: FALSE (7.0)
|   |   |   |   |   |   |   |   |   |   Loss Rate > 0.017075: FALSE (54.0/1.0)
|   |   |   |   |   |   |   Ratio > 0.607619
|   |   |   |   |   |   |   |   Loss Rate <= 0.019242: TRUE (198.0)
|   |   |   |   |   |   |   |   Loss Rate > 0.019242
|   |   |   |   |   |   |   |   |   Burst Size <= 61
|   |   |   |   |   |   |   |   |   |   Loss Rate <= 0.024553: TRUE (65.0)
|   |   |   |   |   |   |   |   |   |   Loss Rate > 0.024553
|   |   |   |   |   |   |   |   |   |   |   Gap Density <= 0.01767: FALSE (2.0)
|   |   |   |   |   |   |   |   |   |   |   Gap Density > 0.01767: TRUE (3.0)
|   |   |   |   |   |   |   |   |   Burst Size > 61
|   |   |   |   |   |   |   |   |   |   Ratio <= 0.743676
|   |   |   |   |   |   |   |   |   |   |   Burst Density <= 0.284044
|   |   |   |   |   |   |   |   |   |   |   |   Burst Size <= 73: TRUE (5.0)
|   |   |   |   |   |   |   |   |   |   |   |   Burst Size > 73
|   |   |   |   |   |   |   |   |   |   |   |   |   Ratio <= 0.670089: FALSE (7.0)
|   |   |   |   |   |   |   |   |   |   |   |   |   Ratio > 0.670089
|   |   |   |   |   |   |   |   |   |   |   |   |   |   Ratio <= 0.699467: TRUE (3.0)
|   |   |   |   |   |   |   |   |   |   |   |   |   |   Ratio > 0.699467: FALSE (3.0)
|   |   |   |   |   |   |   |   |   |   |   Burst Density > 0.284044: FALSE (21.0)
|   |   |   |   |   |   |   |   |   |   Ratio > 0.743676
|   |   |   |   |   |   |   |   |   |   |   Burst Size <= 103: TRUE (23.0)
|   |   |   |   |   |   |   |   |   |   |   Burst Size > 103: FALSE (3.0/1.0)
|   |   |   |   Loss Rate > 0.024829
|   |   |   |   |   Burst Size <= 41
|   |   |   |   |   |   Burst Density <= 0.259679
|   |   |   |   |   |   |   Loss Rate <= 0.039122: TRUE (96.0/4.0)
|   |   |   |   |   |   |   Loss Rate > 0.039122
|   |   |   |   |   |   |   |   Gap Density <= 0.025228: FALSE (13.0)
|   |   |   |   |   |   |   |   Gap Density > 0.025228
|   |   |   |   |   |   |   |   |   Burst Size <= 34: TRUE (43.0/5.0)
|   |   |   |   |   |   |   |   |   Burst Size > 34
|   |   |   |   |   |   |   |   |   |   Burst Density <= 0.239344
|   |   |   |   |   |   |   |   |   |   |   Gap Density <= 0.037996
|   |   |   |   |   |   |   |   |   |   |   |   Burst Density <= 0.230559: TRUE (22.0)
|   |   |   |   |   |   |   |   |   |   |   |   Burst Density > 0.230559
|   |   |   |   |   |   |   |   |   |   |   |   |   Burst Size <= 39: TRUE (10.0/2.0)
|   |   |   |   |   |   |   |   |   |   |   |   |   Burst Size > 39: FALSE (2.0)
|   |   |   |   |   |   |   |   |   |   |   Gap Density > 0.037996: FALSE (5.0)
|   |   |   |   |   |   |   |   |   |   Burst Density > 0.239344
|   |   |   |   |   |   |   |   |   |   |   Burst Size <= 36
|   |   |   |   |   |   |   |   |   |   |   |   Gap Density <= 0.03353: FALSE (7.0)
|   |   |   |   |   |   |   |   |   |   |   |   Gap Density > 0.03353: TRUE (2.0)
|   |   |   |   |   |   |   |   |   |   |   Burst Size > 36: FALSE (20.0)
|   |   |   |   |   |   Burst Density > 0.259679
|   |   |   |   |   |   |   Burst Size <= 30
|   |   |   |   |   |   |   |   Loss Rate <= 0.031141: TRUE (19.0)
|   |   |   |   |   |   |   |   Loss Rate > 0.031141
|   |   |   |   |   |   |   |   |   Burst Density <= 0.276234: TRUE (25.0/3.0)
|   |   |   |   |   |   |   |   |   Burst Density > 0.276234
|   |   |   |   |   |   |   |   |   |   Gap Density <= 0.033408
|   |   |   |   |   |   |   |   |   |   |   Ratio <= 0.824362: FALSE (57.0/5.0)
|   |   |   |   |   |   |   |   |   |   |   Ratio > 0.824362
|   |   |   |   |   |   |   |   |   |   |   |   Burst Density <= 0.298752: TRUE (6.0)
|   |   |   |   |   |   |   |   |   |   |   |   Burst Density > 0.298752
|   |   |   |   |   |   |   |   |   |   |   |   |   Burst Size <= 26: TRUE (2.0)
|   |   |   |   |   |   |   |   |   |   |   |   |   Burst Size > 26: FALSE (2.0)
|   |   |   |   |   |   |   |   |   |   Gap Density > 0.033408: TRUE (11.0/2.0)
|   |   |   |   |   |   |   Burst Size > 30
|   |   |   |   |   |   |   |   Loss Rate <= 0.033089
|   |   |   |   |   |   |   |   |   Burst Density <= 0.290487
|   |   |   |   |   |   |   |   |   |   Ratio <= 0.554213: FALSE (16.0/1.0)
|   |   |   |   |   |   |   |   |   |   Ratio > 0.554213
|   |   |   |   |   |   |   |   |   |   |   Ratio <= 0.692773
|   |   |   |   |   |   |   |   |   |   |   |   Burst Density <= 0.279398: TRUE (8.0/1.0)
|   |   |   |   |   |   |   |   |   |   |   |   Burst Density > 0.279398: FALSE (3.0)
|   |   |   |   |   |   |   |   |   |   |   Ratio > 0.692773: TRUE (17.0)
|   |   |   |   |   |   |   |   |   Burst Density > 0.290487: FALSE (25.0)
|   |   |   |   |   |   |   |   Loss Rate > 0.033089: FALSE (219.0/3.0)
|   |   |   |   |   Burst Size > 41
|   |   |   |   |   |   Loss Rate <= 0.036534
|   |   |   |   |   |   |   Ratio <= 0.594716
|   |   |   |   |   |   |   |   Burst Size <= 56
|   |   |   |   |   |   |   |   |   Burst Density <= 0.245218
|   |   |   |   |   |   |   |   |   |   Loss Rate <= 0.028578: TRUE (16.0/1.0)
|   |   |   |   |   |   |   |   |   |   Loss Rate > 0.028578
|   |   |   |   |   |   |   |   |   |   |   Burst Density <= 0.226422: TRUE (3.0)
|   |   |   |   |   |   |   |   |   |   |   Burst Density > 0.226422: FALSE (26.0/1.0)
|   |   |   |   |   |   |   |   |   Burst Density > 0.245218: FALSE (112.0)
|   |   |   |   |   |   |   |   Burst Size > 56: FALSE (615.0/2.0)
|   |   |   |   |   |   |   Ratio > 0.594716
|   |   |   |   |   |   |   |   Burst Density <= 0.254182
|   |   |   |   |   |   |   |   |   Burst Size <= 61
|   |   |   |   |   |   |   |   |   |   Loss Rate <= 0.033549: TRUE (44.0)
|   |   |   |   |   |   |   |   |   |   Loss Rate > 0.033549
|   |   |   |   |   |   |   |   |   |   |   Burst Density <= 0.240959: TRUE (11.0)
|   |   |   |   |   |   |   |   |   |   |   Burst Density > 0.240959
|   |   |   |   |   |   |   |   |   |   |   |   Ratio <= 0.737806: FALSE (4.0)
|   |   |   |   |   |   |   |   |   |   |   |   Ratio > 0.737806: TRUE (3.0/1.0)
|   |   |   |   |   |   |   |   |   Burst Size > 61
|   |   |   |   |   |   |   |   |   |   Loss Rate <= 0.031265
|   |   |   |   |   |   |   |   |   |   |   Ratio <= 0.685124
|   |   |   |   |   |   |   |   |   |   |   |   Burst Size <= 73: TRUE (6.0)
|   |   |   |   |   |   |   |   |   |   |   |   Burst Size > 73: FALSE (11.0/1.0)
|   |   |   |   |   |   |   |   |   |   |   Ratio > 0.685124: TRUE (31.0)
|   |   |   |   |   |   |   |   |   |   Loss Rate > 0.031265
|   |   |   |   |   |   |   |   |   |   |   Ratio <= 0.740916: FALSE (26.0)
|   |   |   |   |   |   |   |   |   |   |   Ratio > 0.740916
|   |   |   |   |   |   |   |   |   |   |   |   Burst Density <= 0.239177: TRUE (11.0)
|   |   |   |   |   |   |   |   |   |   |   |   Burst Density > 0.239177: FALSE (12.0/1.0)
|   |   |   |   |   |   |   |   Burst Density > 0.254182
|   |   |   |   |   |   |   |   |   Loss Rate <= 0.029892
|   |   |   |   |   |   |   |   |   |   Ratio <= 0.779621
|   |   |   |   |   |   |   |   |   |   |   Burst Density <= 0.272803
|   |   |   |   |   |   |   |   |   |   |   |   Burst Size <= 62
|   |   |   |   |   |   |   |   |   |   |   |   |   Loss Rate <= 0.027743: TRUE (7.0)
|   |   |   |   |   |   |   |   |   |   |   |   |   Loss Rate > 0.027743
|   |   |   |   |   |   |   |   |   |   |   |   |   |   Burst Size <= 51: TRUE (2.0)
|   |   |   |   |   |   |   |   |   |   |   |   |   |   Burst Size > 51: FALSE (4.0)
|   |   |   |   |   |   |   |   |   |   |   |   Burst Size > 62: FALSE (14.0/1.0)
|   |   |   |   |   |   |   |   |   |   |   Burst Density > 0.272803: FALSE (47.0/1.0)
|   |   |   |   |   |   |   |   |   |   Ratio > 0.779621
|   |   |   |   |   |   |   |   |   |   |   Burst Density <= 0.284584: TRUE (22.0)
|   |   |   |   |   |   |   |   |   |   |   Burst Density > 0.284584
|   |   |   |   |   |   |   |   |   |   |   |   Ratio <= 0.832019: FALSE (10.0)
|   |   |   |   |   |   |   |   |   |   |   |   Ratio > 0.832019: TRUE (5.0)
|   |   |   |   |   |   |   |   |   Loss Rate > 0.029892: FALSE (200.0/4.0)
|   |   |   |   |   |   Loss Rate > 0.036534
|   |   |   |   |   |   |   Burst Density <= 0.231063
|   |   |   |   |   |   |   |   Burst Size <= 46
|   |   |   |   |   |   |   |   |   Loss Rate <= 0.042278: TRUE (4.0)
|   |   |   |   |   |   |   |   |   Loss Rate > 0.042278: FALSE (6.0)
|   |   |   |   |   |   |   |   Burst Size > 46: FALSE (154.0/3.0)
|   |   |   |   |   |   |   Burst Density > 0.231063: FALSE (1587.0/1.0)
|   |   |   Burst Density > 0.313473
|   |   |   |   Ratio <= 0.74777
|   |   |   |   |   Burst Density <= 0.396114
|   |   |   |   |   |   Loss Rate <= 0.019658
|   |   |   |   |   |   |   Ratio <= 0.601125
|   |   |   |   |   |   |   |   Burst Size <= 34
|   |   |   |   |   |   |   |   |   Loss Rate <= 0.012619
|   |   |   |   |   |   |   |   |   |   Gap Density <= 0.004253: FALSE (2.0)
|   |   |   |   |   |   |   |   |   |   Gap Density > 0.004253: TRUE (18.0/1.0)
|   |   |   |   |   |   |   |   |   Loss Rate > 0.012619: FALSE (31.0/3.0)
|   |   |   |   |   |   |   |   Burst Size > 34
|   |   |   |   |   |   |   |   |   Loss Rate <= 0.011762
|   |   |   |   |   |   |   |   |   |   Ratio <= 0.385778: FALSE (47.0)
|   |   |   |   |   |   |   |   |   |   Ratio > 0.385778
|   |   |   |   |   |   |   |   |   |   |   Burst Size <= 46: TRUE (5.0)
|   |   |   |   |   |   |   |   |   |   |   Burst Size > 46
|   |   |   |   |   |   |   |   |   |   |   |   Ratio <= 0.524242: FALSE (18.0)
|   |   |   |   |   |   |   |   |   |   |   |   Ratio > 0.524242: TRUE (4.0/1.0)
|   |   |   |   |   |   |   |   |   Loss Rate > 0.011762: FALSE (392.0)
|   |   |   |   |   |   |   Ratio > 0.601125
|   |   |   |   |   |   |   |   Burst Size <= 49
|   |   |   |   |   |   |   |   |   Loss Rate <= 0.015311: TRUE (45.0)
|   |   |   |   |   |   |   |   |   Loss Rate > 0.015311
|   |   |   |   |   |   |   |   |   |   Burst Size <= 28: TRUE (15.0)
|   |   |   |   |   |   |   |   |   |   Burst Size > 28
|   |   |   |   |   |   |   |   |   |   |   Burst Density <= 0.345233: TRUE (6.0/1.0)
|   |   |   |   |   |   |   |   |   |   |   Burst Density > 0.345233: FALSE (12.0/1.0)
|   |   |   |   |   |   |   |   Burst Size > 49
|   |   |   |   |   |   |   |   |   Loss Rate <= 0.015093
|   |   |   |   |   |   |   |   |   |   Burst Density <= 0.332096: TRUE (13.0)
|   |   |   |   |   |   |   |   |   |   Burst Density > 0.332096
|   |   |   |   |   |   |   |   |   |   |   Ratio <= 0.654393: FALSE (10.0)
|   |   |   |   |   |   |   |   |   |   |   Ratio > 0.654393
|   |   |   |   |   |   |   |   |   |   |   |   Burst Size <= 76: TRUE (9.0)
|   |   |   |   |   |   |   |   |   |   |   |   Burst Size > 76
|   |   |   |   |   |   |   |   |   |   |   |   |   Burst Size <= 101: FALSE (7.0)
|   |   |   |   |   |   |   |   |   |   |   |   |   Burst Size > 101: TRUE (3.0/1.0)
|   |   |   |   |   |   |   |   |   Loss Rate > 0.015093: FALSE (53.0)
|   |   |   |   |   |   Loss Rate > 0.019658
|   |   |   |   |   |   |   Burst Size <= 30
|   |   |   |   |   |   |   |   Loss Rate <= 0.024533
|   |   |   |   |   |   |   |   |   Gap Density <= 0.012479: FALSE (15.0)
|   |   |   |   |   |   |   |   |   Gap Density > 0.012479
|   |   |   |   |   |   |   |   |   |   Burst Density <= 0.359627
|   |   |   |   |   |   |   |   |   |   |   Loss Rate <= 0.021948: TRUE (5.0)
|   |   |   |   |   |   |   |   |   |   |   Loss Rate > 0.021948
|   |   |   |   |   |   |   |   |   |   |   |   Gap Density <= 0.013577: FALSE (2.0)
|   |   |   |   |   |   |   |   |   |   |   |   Gap Density > 0.013577: TRUE (2.0)
|   |   |   |   |   |   |   |   |   |   Burst Density > 0.359627: FALSE (11.0/1.0)
|   |   |   |   |   |   |   |   Loss Rate > 0.024533
|   |   |   |   |   |   |   |   |   Gap Density <= 0.031777: FALSE (209.0)
|   |   |   |   |   |   |   |   |   Gap Density > 0.031777
|   |   |   |   |   |   |   |   |   |   Burst Size <= 25: TRUE (2.0)
|   |   |   |   |   |   |   |   |   |   Burst Size > 25: FALSE (9.0)
|   |   |   |   |   |   |   Burst Size > 30: FALSE (2482.0)
|   |   |   |   |   Burst Density > 0.396114
|   |   |   |   |   |   Loss Rate <= 0.014309
|   |   |   |   |   |   |   Burst Density <= 0.493172
|   |   |   |   |   |   |   |   Burst Size <= 34
|   |   |   |   |   |   |   |   |   Ratio <= 0.545114: FALSE (22.0)
|   |   |   |   |   |   |   |   |   Ratio > 0.545114
|   |   |   |   |   |   |   |   |   |   Loss Rate <= 0.013634: TRUE (23.0/4.0)
|   |   |   |   |   |   |   |   |   |   Loss Rate > 0.013634: FALSE (6.0/1.0)
|   |   |   |   |   |   |   |   Burst Size > 34: FALSE (312.0/1.0)
|   |   |   |   |   |   |   Burst Density > 0.493172: FALSE (1907.0/2.0)
|   |   |   |   |   |   Loss Rate > 0.014309: FALSE (17031.0/3.0)
|   |   |   |   Ratio > 0.74777
|   |   |   |   |   Loss Rate <= 0.019543
|   |   |   |   |   |   Burst Density <= 0.451251
|   |   |   |   |   |   |   Loss Rate <= 0.014678: TRUE (86.0)
|   |   |   |   |   |   |   Loss Rate > 0.014678
|   |   |   |   |   |   |   |   Burst Density <= 0.415162
|   |   |   |   |   |   |   |   |   Ratio <= 0.761959
|   |   |   |   |   |   |   |   |   |   Loss Rate <= 0.017683: TRUE (4.0)
|   |   |   |   |   |   |   |   |   |   Loss Rate > 0.017683: FALSE (6.0/1.0)
|   |   |   |   |   |   |   |   |   Ratio > 0.761959
|   |   |   |   |   |   |   |   |   |   Burst Density <= 0.395957: TRUE (49.0)
|   |   |   |   |   |   |   |   |   |   Burst Density > 0.395957
|   |   |   |   |   |   |   |   |   |   |   Ratio <= 0.796078: FALSE (4.0/1.0)
|   |   |   |   |   |   |   |   |   |   |   Ratio > 0.796078: TRUE (9.0)
|   |   |   |   |   |   |   |   Burst Density > 0.415162
|   |   |   |   |   |   |   |   |   Burst Size <= 42
|   |   |   |   |   |   |   |   |   |   Ratio <= 0.792878
|   |   |   |   |   |   |   |   |   |   |   Burst Size <= 22: TRUE (2.0)
|   |   |   |   |   |   |   |   |   |   |   Burst Size > 22: FALSE (3.0)
|   |   |   |   |   |   |   |   |   |   Ratio > 0.792878: TRUE (10.0)
|   |   |   |   |   |   |   |   |   Burst Size > 42
|   |   |   |   |   |   |   |   |   |   Ratio <= 0.819034: FALSE (16.0)
|   |   |   |   |   |   |   |   |   |   Ratio > 0.819034
|   |   |   |   |   |   |   |   |   |   |   Loss Rate <= 0.017185: TRUE (4.0)
|   |   |   |   |   |   |   |   |   |   |   Loss Rate > 0.017185: FALSE (5.0/1.0)
|   |   |   |   |   |   Burst Density > 0.451251
|   |   |   |   |   |   |   Loss Rate <= 0.012795
|   |   |   |   |   |   |   |   Ratio <= 0.806618
|   |   |   |   |   |   |   |   |   Burst Density <= 0.551896
|   |   |   |   |   |   |   |   |   |   Gap Density <= 0.009415: TRUE (18.0/1.0)
|   |   |   |   |   |   |   |   |   |   Gap Density > 0.009415
|   |   |   |   |   |   |   |   |   |   |   Gap Density <= 0.009733: FALSE (3.0)
|   |   |   |   |   |   |   |   |   |   |   Gap Density > 0.009733: TRUE (2.0)
|   |   |   |   |   |   |   |   |   Burst Density > 0.551896
|   |   |   |   |   |   |   |   |   |   Burst Size <= 38
|   |   |   |   |   |   |   |   |   |   |   Burst Density <= 0.812617
|   |   |   |   |   |   |   |   |   |   |   |   Gap Density <= 0.009295: TRUE (7.0)
|   |   |   |   |   |   |   |   |   |   |   |   Gap Density > 0.009295: FALSE (5.0/1.0)
|   |   |   |   |   |   |   |   |   |   |   Burst Density > 0.812617: FALSE (5.0)
|   |   |   |   |   |   |   |   |   |   Burst Size > 38: FALSE (68.0/2.0)
|   |   |   |   |   |   |   |   Ratio > 0.806618
|   |   |   |   |   |   |   |   |   Burst Density <= 0.870147
|   |   |   |   |   |   |   |   |   |   Loss Rate <= 0.011868: TRUE (63.0/1.0)
|   |   |   |   |   |   |   |   |   |   Loss Rate > 0.011868
|   |   |   |   |   |   |   |   |   |   |   Ratio <= 0.832334
|   |   |   |   |   |   |   |   |   |   |   |   Burst Size <= 27: TRUE (5.0)
|   |   |   |   |   |   |   |   |   |   |   |   Burst Size > 27
|   |   |   |   |   |   |   |   |   |   |   |   |   Burst Density <= 0.599857: TRUE (3.0)
|   |   |   |   |   |   |   |   |   |   |   |   |   Burst Density > 0.599857: FALSE (6.0)
|   |   |   |   |   |   |   |   |   |   |   Ratio > 0.832334: TRUE (11.0)
|   |   |   |   |   |   |   |   |   Burst Density > 0.870147
|   |   |   |   |   |   |   |   |   |   Ratio <= 0.838917
|   |   |   |   |   |   |   |   |   |   |   Loss Rate <= 0.010654: TRUE (2.0)
|   |   |   |   |   |   |   |   |   |   |   Loss Rate > 0.010654: FALSE (9.0)
|   |   |   |   |   |   |   |   |   |   Ratio > 0.838917: TRUE (5.0)
|   |   |   |   |   |   |   Loss Rate > 0.012795
|   |   |   |   |   |   |   |   Burst Size <= 31
|   |   |   |   |   |   |   |   |   Burst Size <= 18
|   |   |   |   |   |   |   |   |   |   Burst Density <= 0.635456
|   |   |   |   |   |   |   |   |   |   |   Loss Rate <= 0.017472: TRUE (17.0)
|   |   |   |   |   |   |   |   |   |   |   Loss Rate > 0.017472
|   |   |   |   |   |   |   |   |   |   |   |   Ratio <= 0.775338: FALSE (3.0)
|   |   |   |   |   |   |   |   |   |   |   |   Ratio > 0.775338
|   |   |   |   |   |   |   |   |   |   |   |   |   Burst Density <= 0.555795: TRUE (4.0)
|   |   |   |   |   |   |   |   |   |   |   |   |   Burst Density > 0.555795: FALSE (2.0)
|   |   |   |   |   |   |   |   |   |   Burst Density > 0.635456: FALSE (7.0/1.0)
|   |   |   |   |   |   |   |   |   Burst Size > 18
|   |   |   |   |   |   |   |   |   |   Burst Density <= 0.52761
|   |   |   |   |   |   |   |   |   |   |   Ratio <= 0.812677: FALSE (7.0/1.0)
|   |   |   |   |   |   |   |   |   |   |   Ratio > 0.812677: TRUE (7.0)
|   |   |   |   |   |   |   |   |   |   Burst Density > 0.52761: FALSE (34.0/1.0)
|   |   |   |   |   |   |   |   Burst Size > 31
|   |   |   |   |   |   |   |   |   Burst Density <= 0.518055
|   |   |   |   |   |   |   |   |   |   Loss Rate <= 0.013538: TRUE (4.0)
|   |   |   |   |   |   |   |   |   |   Loss Rate > 0.013538
|   |   |   |   |   |   |   |   |   |   |   Ratio <= 0.831857: FALSE (49.0)
|   |   |   |   |   |   |   |   |   |   |   Ratio > 0.831857
|   |   |   |   |   |   |   |   |   |   |   |   Gap Density <= 0.013429: TRUE (2.0)
|   |   |   |   |   |   |   |   |   |   |   |   Gap Density > 0.013429: FALSE (5.0)
|   |   |   |   |   |   |   |   |   Burst Density > 0.518055: FALSE (309.0/1.0)
|   |   |   |   |   Loss Rate > 0.019543
|   |   |   |   |   |   Burst Size <= 31
|   |   |   |   |   |   |   Burst Density <= 0.416544
|   |   |   |   |   |   |   |   Loss Rate <= 0.027994
|   |   |   |   |   |   |   |   |   Burst Size <= 22: TRUE (12.0)
|   |   |   |   |   |   |   |   |   Burst Size > 22
|   |   |   |   |   |   |   |   |   |   Burst Density <= 0.356887: TRUE (23.0/2.0)
|   |   |   |   |   |   |   |   |   |   Burst Density > 0.356887
|   |   |   |   |   |   |   |   |   |   |   Loss Rate <= 0.023406
|   |   |   |   |   |   |   |   |   |   |   |   Burst Density <= 0.385318: TRUE (2.0)
|   |   |   |   |   |   |   |   |   |   |   |   Burst Density > 0.385318: FALSE (2.0)
|   |   |   |   |   |   |   |   |   |   |   Loss Rate > 0.023406: FALSE (11.0)
|   |   |   |   |   |   |   |   Loss Rate > 0.027994: FALSE (58.0/4.0)
|   |   |   |   |   |   |   Burst Density > 0.416544: FALSE (103.0/1.0)
|   |   |   |   |   |   Burst Size > 31
|   |   |   |   |   |   |   Burst Density <= 0.336601
|   |   |   |   |   |   |   |   Loss Rate <= 0.026823
|   |   |   |   |   |   |   |   |   Loss Rate <= 0.02118: TRUE (8.0)
|   |   |   |   |   |   |   |   |   Loss Rate > 0.02118
|   |   |   |   |   |   |   |   |   |   Ratio <= 0.812877: FALSE (9.0/1.0)
|   |   |   |   |   |   |   |   |   |   Ratio > 0.812877: TRUE (5.0)
|   |   |   |   |   |   |   |   Loss Rate > 0.026823: FALSE (187.0)
|   |   |   |   |   |   |   Burst Density > 0.336601: FALSE (1601.0/1.0)
|   Ratio > 0.854341
|   |   Loss Rate <= 0.018729
|   |   |   Loss Rate <= 0.014218: TRUE (2292.0)
|   |   |   Loss Rate > 0.014218
|   |   |   |   Ratio <= 0.881766
|   |   |   |   |   Burst Density <= 0.555384: TRUE (72.0/3.0)
|   |   |   |   |   Burst Density > 0.555384
|   |   |   |   |   |   Loss Rate <= 0.015049
|   |   |   |   |   |   |   Burst Density <= 0.799596: TRUE (8.0)
|   |   |   |   |   |   |   Burst Density > 0.799596
|   |   |   |   |   |   |   |   Ratio <= 0.869218: FALSE (5.0)
|   |   |   |   |   |   |   |   Ratio > 0.869218: TRUE (3.0)
|   |   |   |   |   |   Loss Rate > 0.015049
|   |   |   |   |   |   |   Burst Size <= 41
|   |   |   |   |   |   |   |   Burst Density <= 0.706514: TRUE (3.0)
|   |   |   |   |   |   |   |   Burst Density > 0.706514: FALSE (6.0)
|   |   |   |   |   |   |   Burst Size > 41: FALSE (32.0)
|   |   |   |   Ratio > 0.881766
|   |   |   |   |   Ratio <= 0.897988
|   |   |   |   |   |   Loss Rate <= 0.017437: TRUE (61.0)
|   |   |   |   |   |   Loss Rate > 0.017437
|   |   |   |   |   |   |   Burst Density <= 0.735266: TRUE (17.0/1.0)
|   |   |   |   |   |   |   Burst Density > 0.735266: FALSE (4.0)
|   |   |   |   |   Ratio > 0.897988: TRUE (536.0)
|   |   Loss Rate > 0.018729
|   |   |   Ratio <= 0.918704
|   |   |   |   Burst Size <= 38
|   |   |   |   |   Burst Density <= 0.340799
|   |   |   |   |   |   Loss Rate <= 0.036467: TRUE (136.0/2.0)
|   |   |   |   |   |   Loss Rate > 0.036467
|   |   |   |   |   |   |   Burst Size <= 34: TRUE (5.0)
|   |   |   |   |   |   |   Burst Size > 34: FALSE (5.0)
|   |   |   |   |   Burst Density > 0.340799
|   |   |   |   |   |   Burst Size <= 22: TRUE (27.0)
|   |   |   |   |   |   Burst Size > 22
|   |   |   |   |   |   |   Ratio <= 0.895091
|   |   |   |   |   |   |   |   Burst Size <= 27
|   |   |   |   |   |   |   |   |   Loss Rate <= 0.025974
|   |   |   |   |   |   |   |   |   |   Burst Density <= 0.440916: TRUE (9.0)
|   |   |   |   |   |   |   |   |   |   Burst Density > 0.440916: FALSE (5.0/1.0)
|   |   |   |   |   |   |   |   |   Loss Rate > 0.025974: FALSE (4.0)
|   |   |   |   |   |   |   |   Burst Size > 27: FALSE (48.0/4.0)
|   |   |   |   |   |   |   Ratio > 0.895091: TRUE (24.0/2.0)
|   |   |   |   Burst Size > 38
|   |   |   |   |   Loss Rate <= 0.030175
|   |   |   |   |   |   Burst Density <= 0.326644
|   |   |   |   |   |   |   Burst Density <= 0.296733: TRUE (121.0)
|   |   |   |   |   |   |   Burst Density > 0.296733
|   |   |   |   |   |   |   |   Loss Rate <= 0.02741: TRUE (29.0)
|   |   |   |   |   |   |   |   Loss Rate > 0.02741
|   |   |   |   |   |   |   |   |   Ratio <= 0.887676: FALSE (5.0)
|   |   |   |   |   |   |   |   |   Ratio > 0.887676
|   |   |   |   |   |   |   |   |   |   Gap Density <= 0.026574: TRUE (4.0)
|   |   |   |   |   |   |   |   |   |   Gap Density > 0.026574: FALSE (2.0)
|   |   |   |   |   |   Burst Density > 0.326644
|   |   |   |   |   |   |   Loss Rate <= 0.024128
|   |   |   |   |   |   |   |   Burst Density <= 0.430415
|   |   |   |   |   |   |   |   |   Loss Rate <= 0.021998: TRUE (25.0)
|   |   |   |   |   |   |   |   |   Loss Rate > 0.021998
|   |   |   |   |   |   |   |   |   |   Burst Density <= 0.376855: TRUE (10.0)
|   |   |   |   |   |   |   |   |   |   Burst Density > 0.376855
|   |   |   |   |   |   |   |   |   |   |   Gap Density <= 0.020092: FALSE (4.0)
|   |   |   |   |   |   |   |   |   |   |   Gap Density > 0.020092: TRUE (9.0/1.0)
|   |   |   |   |   |   |   |   Burst Density > 0.430415
|   |   |   |   |   |   |   |   |   Ratio <= 0.900536
|   |   |   |   |   |   |   |   |   |   Loss Rate <= 0.020409
|   |   |   |   |   |   |   |   |   |   |   Burst Density <= 0.467412
|   |   |   |   |   |   |   |   |   |   |   |   Ratio <= 0.882682: FALSE (3.0/1.0)
|   |   |   |   |   |   |   |   |   |   |   |   Ratio > 0.882682: TRUE (3.0)
|   |   |   |   |   |   |   |   |   |   |   Burst Density > 0.467412
|   |   |   |   |   |   |   |   |   |   |   |   Ratio <= 0.894877: FALSE (34.0)
|   |   |   |   |   |   |   |   |   |   |   |   Ratio > 0.894877: TRUE (3.0/1.0)
|   |   |   |   |   |   |   |   |   |   Loss Rate > 0.020409: FALSE (79.0)
|   |   |   |   |   |   |   |   |   Ratio > 0.900536
|   |   |   |   |   |   |   |   |   |   Loss Rate <= 0.022164
|   |   |   |   |   |   |   |   |   |   |   Burst Density <= 0.570315: TRUE (27.0)
|   |   |   |   |   |   |   |   |   |   |   Burst Density > 0.570315
|   |   |   |   |   |   |   |   |   |   |   |   Ratio <= 0.903956: FALSE (5.0)
|   |   |   |   |   |   |   |   |   |   |   |   Ratio > 0.903956
|   |   |   |   |   |   |   |   |   |   |   |   |   Loss Rate <= 0.020956: TRUE (5.0)
|   |   |   |   |   |   |   |   |   |   |   |   |   Loss Rate > 0.020956: FALSE (3.0/1.0)
|   |   |   |   |   |   |   |   |   |   Loss Rate > 0.022164: FALSE (15.0/1.0)
|   |   |   |   |   |   |   Loss Rate > 0.024128
|   |   |   |   |   |   |   |   Ratio <= 0.907771: FALSE (189.0)
|   |   |   |   |   |   |   |   Ratio > 0.907771
|   |   |   |   |   |   |   |   |   Burst Density <= 0.355832
|   |   |   |   |   |   |   |   |   |   Loss Rate <= 0.026996: TRUE (4.0)
|   |   |   |   |   |   |   |   |   |   Loss Rate > 0.026996: FALSE (4.0)
|   |   |   |   |   |   |   |   |   Burst Density > 0.355832: FALSE (28.0/1.0)
|   |   |   |   |   Loss Rate > 0.030175
|   |   |   |   |   |   Burst Density <= 0.272158
|   |   |   |   |   |   |   Gap Density <= 0.030604
|   |   |   |   |   |   |   |   Burst Density <= 0.25828: TRUE (41.0)
|   |   |   |   |   |   |   |   Burst Density > 0.25828
|   |   |   |   |   |   |   |   |   Gap Density <= 0.028975: TRUE (7.0)
|   |   |   |   |   |   |   |   |   Gap Density > 0.028975: FALSE (10.0)
|   |   |   |   |   |   |   Gap Density > 0.030604
|   |   |   |   |   |   |   |   Burst Density <= 0.244291
|   |   |   |   |   |   |   |   |   Loss Rate <= 0.036038
|   |   |   |   |   |   |   |   |   |   Burst Density <= 0.230459: TRUE (5.0)
|   |   |   |   |   |   |   |   |   |   Burst Density > 0.230459
|   |   |   |   |   |   |   |   |   |   |   Burst Size <= 52: TRUE (2.0)
|   |   |   |   |   |   |   |   |   |   |   Burst Size > 52: FALSE (8.0/2.0)
|   |   |   |   |   |   |   |   |   Loss Rate > 0.036038: FALSE (85.0/12.0)
|   |   |   |   |   |   |   |   Burst Density > 0.244291: FALSE (87.0/2.0)
|   |   |   |   |   |   Burst Density > 0.272158: FALSE (291.0/3.0)
|   |   |   Ratio > 0.918704
|   |   |   |   Ratio <= 0.92613
|   |   |   |   |   Gap Density <= 0.024246: TRUE (62.0)
|   |   |   |   |   Gap Density > 0.024246
|   |   |   |   |   |   Burst Density <= 0.376477
|   |   |   |   |   |   |   Burst Size <= 56
|   |   |   |   |   |   |   |   Burst Size <= 54: TRUE (26.0/1.0)
|   |   |   |   |   |   |   |   Burst Size > 54: FALSE (3.0)
|   |   |   |   |   |   |   Burst Size > 56: TRUE (15.0/1.0)
|   |   |   |   |   |   Burst Density > 0.376477: FALSE (7.0)
|   |   |   |   Ratio > 0.92613: TRUE (636.0)

Number of Leaves  : 	423

Size of the tree : 	845


Time taken to build model: 1.34 seconds

=== Evaluation on test split ===
=== Summary ===

Correctly Classified Instances       11478               98.1361 %
Incorrectly Classified Instances       218                1.8639 %
Kappa statistic                          0.962 
K&B Relative Info Score            1119767.2304 %
K&B Information Score                11056.325  bits      0.9453 bits/instance
Class complexity | order 0           11545.8578 bits      0.9872 bits/instance
Class complexity | scheme           106892.6503 bits      9.1392 bits/instance
Complexity improvement     (Sf)     -95346.7925 bits     -8.1521 bits/instance
Mean absolute error                      0.0214
Root mean squared error                  0.1308
Relative absolute error                  4.347  %
Root relative squared error             26.3931 %
Total Number of Instances            11696     

=== Detailed Accuracy By Class ===

               TP Rate   FP Rate   Precision   Recall  F-Measure   ROC Area  Class
                 0.978     0.016      0.979     0.978     0.978      0.987    TRUE
                 0.984     0.022      0.983     0.984     0.984      0.987    FALSE
Weighted Avg.    0.981     0.019      0.981     0.981     0.981      0.987

=== Confusion Matrix ===

    a    b   <-- classified as
 4956  113 |    a = TRUE
  105 6522 |    b = FALSE

=== Source code ===

// Generated with Weka 3.6.11
//
// This code is public domain and comes with no warranty.
//
// Timestamp: Tue Jul 01 21:01:59 EDT 2014
*/

class FECTDClassifier {

  public static double classify(Object[] i)
    throws Exception {

    double p = Double.NaN;
    p = FECTDClassifier.N5eefc3c00(i);
    return p;
  }
  static double N5eefc3c00(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.223786011) {
    p = FECTDClassifier.N4f1ca2f11(i);
    } else if (((Double) i[3]).doubleValue() > 0.223786011) {
    p = FECTDClassifier.N6987f8259(i);
    } 
    return p;
  }
  static double N4f1ca2f11(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.201818168) {
    p = FECTDClassifier.N610da4ca2(i);
    } else if (((Double) i[3]).doubleValue() > 0.201818168) {
    p = FECTDClassifier.N4fd7cb2817(i);
    } 
    return p;
  }
  static double N610da4ca2(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.043211276) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 0.043211276) {
    p = FECTDClassifier.N46bdfb8d3(i);
    } 
    return p;
  }
  static double N46bdfb8d3(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.182374626) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 0.182374626) {
    p = FECTDClassifier.N62ce0a434(i);
    } 
    return p;
  }
  static double N62ce0a434(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 0;
    } else if (((Double) i[2]).doubleValue() <= 0.038849678) {
    p = FECTDClassifier.N4503e7625(i);
    } else if (((Double) i[2]).doubleValue() > 0.038849678) {
    p = FECTDClassifier.N4ce96f2010(i);
    } 
    return p;
  }
  static double N4503e7625(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 0;
    } else if (((Double) i[4]).doubleValue() <= 0.86865006) {
    p = FECTDClassifier.N34bf6546(i);
    } else if (((Double) i[4]).doubleValue() > 0.86865006) {
    p = FECTDClassifier.N7af8f0628(i);
    } 
    return p;
  }
  static double N34bf6546(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 72.0) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() > 72.0) {
    p = FECTDClassifier.N7019067c7(i);
    } 
    return p;
  }
  static double N7019067c7(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.144401083) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 0.144401083) {
      p = 1;
    } 
    return p;
  }
  static double N7af8f0628(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 50.0) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() > 50.0) {
    p = FECTDClassifier.N6d6df1009(i);
    } 
    return p;
  }
  static double N6d6df1009(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 0;
    } else if (((Double) i[2]).doubleValue() <= 0.038100589) {
      p = 0;
    } else if (((Double) i[2]).doubleValue() > 0.038100589) {
      p = 1;
    } 
    return p;
  }
  static double N4ce96f2010(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.194571525) {
    p = FECTDClassifier.N717ec55211(i);
    } else if (((Double) i[3]).doubleValue() > 0.194571525) {
      p = 1;
    } 
    return p;
  }
  static double N717ec55211(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 0;
    } else if (((Double) i[2]).doubleValue() <= 0.040809907) {
      p = 0;
    } else if (((Double) i[2]).doubleValue() > 0.040809907) {
    p = FECTDClassifier.N39ccc4b412(i);
    } 
    return p;
  }
  static double N39ccc4b412(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 50.0) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() > 50.0) {
    p = FECTDClassifier.N3286231113(i);
    } 
    return p;
  }
  static double N3286231113(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.048091359) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 0.048091359) {
    p = FECTDClassifier.N4684b0114(i);
    } 
    return p;
  }
  static double N4684b0114(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 1;
    } else if (((Double) i[3]).doubleValue() <= 0.185730964) {
    p = FECTDClassifier.N5b1430e915(i);
    } else if (((Double) i[3]).doubleValue() > 0.185730964) {
      p = 1;
    } 
    return p;
  }
  static double N5b1430e915(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 0;
    } else if (((Double) i[2]).doubleValue() <= 0.048939809) {
    p = FECTDClassifier.Na973fb616(i);
    } else if (((Double) i[2]).doubleValue() > 0.048939809) {
      p = 1;
    } 
    return p;
  }
  static double Na973fb616(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 1;
    } else if (((Double) i[0]).doubleValue() <= 0.049590657) {
      p = 1;
    } else if (((Double) i[0]).doubleValue() > 0.049590657) {
      p = 0;
    } 
    return p;
  }
  static double N4fd7cb2817(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.037378612) {
    p = FECTDClassifier.N605ad80b18(i);
    } else if (((Double) i[0]).doubleValue() > 0.037378612) {
    p = FECTDClassifier.N762cac7830(i);
    } 
    return p;
  }
  static double N605ad80b18(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 0;
    } else if (((Double) i[4]).doubleValue() <= 0.562953164) {
    p = FECTDClassifier.N6c1deb9819(i);
    } else if (((Double) i[4]).doubleValue() > 0.562953164) {
      p = 0;
    } 
    return p;
  }
  static double N6c1deb9819(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.025063202) {
    p = FECTDClassifier.N20567f9c20(i);
    } else if (((Double) i[0]).doubleValue() > 0.025063202) {
    p = FECTDClassifier.N7b00695022(i);
    } 
    return p;
  }
  static double N20567f9c20(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 0;
    } else if (((Double) i[4]).doubleValue() <= 0.154907126) {
    p = FECTDClassifier.Ned3eb5a21(i);
    } else if (((Double) i[4]).doubleValue() > 0.154907126) {
      p = 0;
    } 
    return p;
  }
  static double Ned3eb5a21(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.019056982) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 0.019056982) {
      p = 1;
    } 
    return p;
  }
  static double N7b00695022(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.235110395) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() > 0.235110395) {
    p = FECTDClassifier.N5802424623(i);
    } 
    return p;
  }
  static double N5802424623(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.209625408) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 0.209625408) {
    p = FECTDClassifier.N7c4d2ecd24(i);
    } 
    return p;
  }
  static double N7c4d2ecd24(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 53.0) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() > 53.0) {
    p = FECTDClassifier.N2f3bd47c25(i);
    } 
    return p;
  }
  static double N2f3bd47c25(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.030467496) {
    p = FECTDClassifier.Nfb311e26(i);
    } else if (((Double) i[0]).doubleValue() > 0.030467496) {
    p = FECTDClassifier.N44cf13cb28(i);
    } 
    return p;
  }
  static double Nfb311e26(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 1;
    } else if (((Double) i[2]).doubleValue() <= 0.008334789) {
    p = FECTDClassifier.N6b55a91227(i);
    } else if (((Double) i[2]).doubleValue() > 0.008334789) {
      p = 0;
    } 
    return p;
  }
  static double N6b55a91227(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 60.0) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() > 60.0) {
      p = 1;
    } 
    return p;
  }
  static double N44cf13cb28(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 1;
    } else if (((Double) i[3]).doubleValue() <= 0.212505773) {
    p = FECTDClassifier.N771c97b729(i);
    } else if (((Double) i[3]).doubleValue() > 0.212505773) {
      p = 1;
    } 
    return p;
  }
  static double N771c97b729(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 1;
    } else if (((Double) i[2]).doubleValue() <= 0.012725552) {
      p = 1;
    } else if (((Double) i[2]).doubleValue() > 0.012725552) {
      p = 0;
    } 
    return p;
  }
  static double N762cac7830(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 47.0) {
    p = FECTDClassifier.N6eef36e431(i);
    } else if (((Double) i[1]).doubleValue() > 47.0) {
    p = FECTDClassifier.N39f4d01738(i);
    } 
    return p;
  }
  static double N6eef36e431(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 0;
    } else if (((Double) i[2]).doubleValue() <= 0.039210796) {
    p = FECTDClassifier.N1afceba232(i);
    } else if (((Double) i[2]).doubleValue() > 0.039210796) {
      p = 1;
    } 
    return p;
  }
  static double N1afceba232(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 42.0) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() > 42.0) {
    p = FECTDClassifier.N52d1e68533(i);
    } 
    return p;
  }
  static double N52d1e68533(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.223101825) {
    p = FECTDClassifier.N5275503d34(i);
    } else if (((Double) i[3]).doubleValue() > 0.223101825) {
      p = 1;
    } 
    return p;
  }
  static double N5275503d34(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 0;
    } else if (((Double) i[2]).doubleValue() <= 0.036694713) {
    p = FECTDClassifier.N13e6ff1635(i);
    } else if (((Double) i[2]).doubleValue() > 0.036694713) {
    p = FECTDClassifier.N31434ef537(i);
    } 
    return p;
  }
  static double N13e6ff1635(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.220239103) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 0.220239103) {
    p = FECTDClassifier.N2275078f36(i);
    } 
    return p;
  }
  static double N2275078f36(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.043195225) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 0.043195225) {
      p = 1;
    } 
    return p;
  }
  static double N31434ef537(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.208367497) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 0.208367497) {
      p = 1;
    } 
    return p;
  }
  static double N39f4d01738(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.215105549) {
    p = FECTDClassifier.N7789b3ba39(i);
    } else if (((Double) i[3]).doubleValue() > 0.215105549) {
    p = FECTDClassifier.N592ddff656(i);
    } 
    return p;
  }
  static double N7789b3ba39(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 0;
    } else if (((Double) i[2]).doubleValue() <= 0.037018836) {
    p = FECTDClassifier.N7176b5a540(i);
    } else if (((Double) i[2]).doubleValue() > 0.037018836) {
    p = FECTDClassifier.N3fd851ed52(i);
    } 
    return p;
  }
  static double N7176b5a540(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 1;
    } else if (((Double) i[2]).doubleValue() <= 0.017338397) {
    p = FECTDClassifier.N288f9dd541(i);
    } else if (((Double) i[2]).doubleValue() > 0.017338397) {
    p = FECTDClassifier.N6cc31fc042(i);
    } 
    return p;
  }
  static double N288f9dd541(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.203375474) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 0.203375474) {
      p = 1;
    } 
    return p;
  }
  static double N6cc31fc042(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 0;
    } else if (((Double) i[4]).doubleValue() <= 0.858070622) {
    p = FECTDClassifier.N7e59ae0843(i);
    } else if (((Double) i[4]).doubleValue() > 0.858070622) {
    p = FECTDClassifier.N77cacffd48(i);
    } 
    return p;
  }
  static double N7e59ae0843(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 79.0) {
    p = FECTDClassifier.N31b0d00644(i);
    } else if (((Double) i[1]).doubleValue() > 79.0) {
    p = FECTDClassifier.N47a46fe47(i);
    } 
    return p;
  }
  static double N31b0d00644(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.209225878) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 0.209225878) {
    p = FECTDClassifier.N4f29536645(i);
    } 
    return p;
  }
  static double N4f29536645(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 1;
    } else if (((Double) i[2]).doubleValue() <= 0.022651426) {
    p = FECTDClassifier.N2222762446(i);
    } else if (((Double) i[2]).doubleValue() > 0.022651426) {
      p = 0;
    } 
    return p;
  }
  static double N2222762446(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 62.0) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() > 62.0) {
      p = 1;
    } 
    return p;
  }
  static double N47a46fe47(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.210250676) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 0.210250676) {
      p = 1;
    } 
    return p;
  }
  static double N77cacffd48(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 0;
    } else if (((Double) i[2]).doubleValue() <= 0.035435542) {
    p = FECTDClassifier.N241d287c49(i);
    } else if (((Double) i[2]).doubleValue() > 0.035435542) {
      p = 1;
    } 
    return p;
  }
  static double N241d287c49(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.206102356) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 0.206102356) {
    p = FECTDClassifier.N764cf76950(i);
    } 
    return p;
  }
  static double N764cf76950(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 51.0) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() > 51.0) {
    p = FECTDClassifier.N37074b2c51(i);
    } 
    return p;
  }
  static double N37074b2c51(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 0;
    } else if (((Double) i[4]).doubleValue() <= 0.870873927) {
      p = 0;
    } else if (((Double) i[4]).doubleValue() > 0.870873927) {
      p = 1;
    } 
    return p;
  }
  static double N3fd851ed52(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.873605106) {
    p = FECTDClassifier.N12eabf5a53(i);
    } else if (((Double) i[4]).doubleValue() > 0.873605106) {
    p = FECTDClassifier.N161188d355(i);
    } 
    return p;
  }
  static double N12eabf5a53(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 0;
    } else if (((Double) i[4]).doubleValue() <= 0.808487803) {
    p = FECTDClassifier.N71b8b76954(i);
    } else if (((Double) i[4]).doubleValue() > 0.808487803) {
      p = 1;
    } 
    return p;
  }
  static double N71b8b76954(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.074244583) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 0.074244583) {
      p = 1;
    } 
    return p;
  }
  static double N161188d355(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 1;
    } else if (((Double) i[2]).doubleValue() <= 0.037616204) {
      p = 1;
    } else if (((Double) i[2]).doubleValue() > 0.037616204) {
      p = 0;
    } 
    return p;
  }
  static double N592ddff656(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.396147922) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() > 0.396147922) {
    p = FECTDClassifier.N52cabd3757(i);
    } 
    return p;
  }
  static double N52cabd3757(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 1;
    } else if (((Double) i[0]).doubleValue() <= 0.042172953) {
    p = FECTDClassifier.N7c4c905758(i);
    } else if (((Double) i[0]).doubleValue() > 0.042172953) {
      p = 1;
    } 
    return p;
  }
  static double N7c4c905758(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.220699847) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 0.220699847) {
      p = 1;
    } 
    return p;
  }
  static double N6987f8259(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.85434078) {
    p = FECTDClassifier.N5db333060(i);
    } else if (((Double) i[4]).doubleValue() > 0.85434078) {
    p = FECTDClassifier.N45a4e5f9364(i);
    } 
    return p;
  }
  static double N5db333060(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.009982945) {
    p = FECTDClassifier.N79fd975061(i);
    } else if (((Double) i[0]).doubleValue() > 0.009982945) {
    p = FECTDClassifier.N1fe36267181(i);
    } 
    return p;
  }
  static double N79fd975061(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.375292838) {
    p = FECTDClassifier.N77d943c162(i);
    } else if (((Double) i[3]).doubleValue() > 0.375292838) {
    p = FECTDClassifier.N2c85793777(i);
    } 
    return p;
  }
  static double N77d943c162(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 0;
    } else if (((Double) i[4]).doubleValue() <= 0.483140281) {
    p = FECTDClassifier.N58ef715f63(i);
    } else if (((Double) i[4]).doubleValue() > 0.483140281) {
      p = 0;
    } 
    return p;
  }
  static double N58ef715f63(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.006223917) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 0.006223917) {
    p = FECTDClassifier.N4ffc419664(i);
    } 
    return p;
  }
  static double N4ffc419664(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.309993923) {
    p = FECTDClassifier.N3a3607e065(i);
    } else if (((Double) i[3]).doubleValue() > 0.309993923) {
    p = FECTDClassifier.N38e9d6c071(i);
    } 
    return p;
  }
  static double N3a3607e065(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 0;
    } else if (((Double) i[4]).doubleValue() <= 0.27108425) {
    p = FECTDClassifier.N313f20fb66(i);
    } else if (((Double) i[4]).doubleValue() > 0.27108425) {
      p = 0;
    } 
    return p;
  }
  static double N313f20fb66(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 73.0) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() > 73.0) {
    p = FECTDClassifier.N278e57ff67(i);
    } 
    return p;
  }
  static double N278e57ff67(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.276546091) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 0.276546091) {
    p = FECTDClassifier.N72333aa268(i);
    } 
    return p;
  }
  static double N72333aa268(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.007767857) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 0.007767857) {
    p = FECTDClassifier.N14e9c4169(i);
    } 
    return p;
  }
  static double N14e9c4169(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 1;
    } else if (((Double) i[1]).doubleValue() <= 91.0) {
      p = 1;
    } else if (((Double) i[1]).doubleValue() > 91.0) {
    p = FECTDClassifier.N4ff4701270(i);
    } 
    return p;
  }
  static double N4ff4701270(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 105.0) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() > 105.0) {
      p = 1;
    } 
    return p;
  }
  static double N38e9d6c071(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 49.0) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() > 49.0) {
    p = FECTDClassifier.N11f47172(i);
    } 
    return p;
  }
  static double N11f47172(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.242192765) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() > 0.242192765) {
    p = FECTDClassifier.N1ac726c073(i);
    } 
    return p;
  }
  static double N1ac726c073(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.007263242) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 0.007263242) {
    p = FECTDClassifier.N8c114fc74(i);
    } 
    return p;
  }
  static double N8c114fc74(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 1;
    } else if (((Double) i[3]).doubleValue() <= 0.336690962) {
    p = FECTDClassifier.N3c48b0e175(i);
    } else if (((Double) i[3]).doubleValue() > 0.336690962) {
      p = 1;
    } 
    return p;
  }
  static double N3c48b0e175(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.393255119) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() > 0.393255119) {
    p = FECTDClassifier.N4854a2b276(i);
    } 
    return p;
  }
  static double N4854a2b276(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 0;
    } else if (((Double) i[2]).doubleValue() <= 0.00376439) {
      p = 0;
    } else if (((Double) i[2]).doubleValue() > 0.00376439) {
      p = 1;
    } 
    return p;
  }
  static double N2c85793777(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.004719101) {
    p = FECTDClassifier.N6ed920b678(i);
    } else if (((Double) i[0]).doubleValue() > 0.004719101) {
    p = FECTDClassifier.N78399d6e113(i);
    } 
    return p;
  }
  static double N6ed920b678(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 0;
    } else if (((Double) i[4]).doubleValue() <= 0.479094142) {
    p = FECTDClassifier.N70f2c59479(i);
    } else if (((Double) i[4]).doubleValue() > 0.479094142) {
    p = FECTDClassifier.N6c01ef25109(i);
    } 
    return p;
  }
  static double N70f2c59479(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.002808989) {
    p = FECTDClassifier.N5289b17a80(i);
    } else if (((Double) i[0]).doubleValue() > 0.002808989) {
    p = FECTDClassifier.N5be374be87(i);
    } 
    return p;
  }
  static double N5289b17a80(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.002112761) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 0.002112761) {
    p = FECTDClassifier.N4de2eaeb81(i);
    } 
    return p;
  }
  static double N4de2eaeb81(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 0;
    } else if (((Double) i[4]).doubleValue() <= 0.177756764) {
    p = FECTDClassifier.N6cad123f82(i);
    } else if (((Double) i[4]).doubleValue() > 0.177756764) {
      p = 0;
    } 
    return p;
  }
  static double N6cad123f82(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.62043798) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 0.62043798) {
    p = FECTDClassifier.N56891dd683(i);
    } 
    return p;
  }
  static double N56891dd683(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.00241874) {
    p = FECTDClassifier.N4405f8fc84(i);
    } else if (((Double) i[0]).doubleValue() > 0.00241874) {
    p = FECTDClassifier.N4fa315aa86(i);
    } 
    return p;
  }
  static double N4405f8fc84(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 1;
    } else if (((Double) i[2]).doubleValue() <= 1.41204E-4) {
    p = FECTDClassifier.N641d8b4785(i);
    } else if (((Double) i[2]).doubleValue() > 1.41204E-4) {
      p = 0;
    } 
    return p;
  }
  static double N641d8b4785(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 60.0) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() > 60.0) {
      p = 1;
    } 
    return p;
  }
  static double N4fa315aa86(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 26.0) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() > 26.0) {
      p = 1;
    } 
    return p;
  }
  static double N5be374be87(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.356892674) {
    p = FECTDClassifier.N3205951388(i);
    } else if (((Double) i[4]).doubleValue() > 0.356892674) {
    p = FECTDClassifier.N21339312102(i);
    } 
    return p;
  }
  static double N3205951388(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.537062109) {
    p = FECTDClassifier.Nc7e2c0d89(i);
    } else if (((Double) i[3]).doubleValue() > 0.537062109) {
    p = FECTDClassifier.N25ef6c2498(i);
    } 
    return p;
  }
  static double Nc7e2c0d89(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.003795327) {
    p = FECTDClassifier.N2f7e0fe390(i);
    } else if (((Double) i[0]).doubleValue() > 0.003795327) {
    p = FECTDClassifier.N72b48b3694(i);
    } 
    return p;
  }
  static double N2f7e0fe390(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 0;
    } else if (((Double) i[2]).doubleValue() <= 4.08701E-4) {
    p = FECTDClassifier.N7d45187091(i);
    } else if (((Double) i[2]).doubleValue() > 4.08701E-4) {
      p = 0;
    } 
    return p;
  }
  static double N7d45187091(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.488138616) {
    p = FECTDClassifier.N4347a15092(i);
    } else if (((Double) i[3]).doubleValue() > 0.488138616) {
      p = 1;
    } 
    return p;
  }
  static double N4347a15092(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 71.0) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() > 71.0) {
    p = FECTDClassifier.N13afadb293(i);
    } 
    return p;
  }
  static double N13afadb293(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.093919568) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() > 0.093919568) {
      p = 0;
    } 
    return p;
  }
  static double N72b48b3694(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.415861279) {
    p = FECTDClassifier.N2b1fc10f95(i);
    } else if (((Double) i[3]).doubleValue() > 0.415861279) {
    p = FECTDClassifier.N31bbd5e796(i);
    } 
    return p;
  }
  static double N2b1fc10f95(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 1;
    } else if (((Double) i[2]).doubleValue() <= 4.88669E-4) {
      p = 1;
    } else if (((Double) i[2]).doubleValue() > 4.88669E-4) {
      p = 0;
    } 
    return p;
  }
  static double N31bbd5e796(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 1;
    } else if (((Double) i[2]).doubleValue() <= 0.001113217) {
      p = 1;
    } else if (((Double) i[2]).doubleValue() > 0.001113217) {
    p = FECTDClassifier.N22d84a3397(i);
    } 
    return p;
  }
  static double N22d84a3397(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 52.0) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() > 52.0) {
      p = 1;
    } 
    return p;
  }
  static double N25ef6c2498(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 1;
    } else if (((Double) i[0]).doubleValue() <= 0.003050762) {
    p = FECTDClassifier.N9a4c2f199(i);
    } else if (((Double) i[0]).doubleValue() > 0.003050762) {
      p = 1;
    } 
    return p;
  }
  static double N9a4c2f199(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.244268069) {
    p = FECTDClassifier.N1ffa6129100(i);
    } else if (((Double) i[4]).doubleValue() > 0.244268069) {
    p = FECTDClassifier.N6f00db25101(i);
    } 
    return p;
  }
  static double N1ffa6129100(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 33.0) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() > 33.0) {
      p = 1;
    } 
    return p;
  }
  static double N6f00db25101(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.900336623) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 0.900336623) {
      p = 1;
    } 
    return p;
  }
  static double N21339312102(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.003991774) {
    p = FECTDClassifier.N40fc93c5103(i);
    } else if (((Double) i[0]).doubleValue() > 0.003991774) {
    p = FECTDClassifier.N7c4f4dad106(i);
    } 
    return p;
  }
  static double N40fc93c5103(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.003461075) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 0.003461075) {
    p = FECTDClassifier.N6498bd8104(i);
    } 
    return p;
  }
  static double N6498bd8104(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 0;
    } else if (((Double) i[2]).doubleValue() <= 0.001439483) {
    p = FECTDClassifier.N467c1521105(i);
    } else if (((Double) i[2]).doubleValue() > 0.001439483) {
      p = 0;
    } 
    return p;
  }
  static double N467c1521105(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.629372954) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 0.629372954) {
      p = 1;
    } 
    return p;
  }
  static double N7c4f4dad106(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.530113935) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 0.530113935) {
    p = FECTDClassifier.N3a74d89d107(i);
    } 
    return p;
  }
  static double N3a74d89d107(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 1;
    } else if (((Double) i[1]).doubleValue() <= 59.0) {
    p = FECTDClassifier.N4d394966108(i);
    } else if (((Double) i[1]).doubleValue() > 59.0) {
      p = 1;
    } 
    return p;
  }
  static double N4d394966108(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.791286409) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 0.791286409) {
      p = 1;
    } 
    return p;
  }
  static double N6c01ef25109(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.004051966) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 0.004051966) {
    p = FECTDClassifier.N72fb9d88110(i);
    } 
    return p;
  }
  static double N72fb9d88110(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 0;
    } else if (((Double) i[4]).doubleValue() <= 0.539444604) {
    p = FECTDClassifier.N652386b1111(i);
    } else if (((Double) i[4]).doubleValue() > 0.539444604) {
      p = 0;
    } 
    return p;
  }
  static double N652386b1111(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.724692285) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 0.724692285) {
    p = FECTDClassifier.N7f600256112(i);
    } 
    return p;
  }
  static double N7f600256112(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.004331862) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 0.004331862) {
      p = 1;
    } 
    return p;
  }
  static double N78399d6e113(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.586308346) {
    p = FECTDClassifier.Ne8ede6c114(i);
    } else if (((Double) i[4]).doubleValue() > 0.586308346) {
    p = FECTDClassifier.N5cdf3c40142(i);
    } 
    return p;
  }
  static double Ne8ede6c114(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 1;
    } else if (((Double) i[3]).doubleValue() <= 0.595736742) {
    p = FECTDClassifier.N45ac8beb115(i);
    } else if (((Double) i[3]).doubleValue() > 0.595736742) {
    p = FECTDClassifier.N75c76ae3137(i);
    } 
    return p;
  }
  static double N45ac8beb115(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.361053457) {
    p = FECTDClassifier.N43161509116(i);
    } else if (((Double) i[4]).doubleValue() > 0.361053457) {
    p = FECTDClassifier.N4ff9b491119(i);
    } 
    return p;
  }
  static double N43161509116(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 1;
    } else if (((Double) i[1]).doubleValue() <= 29.0) {
    p = FECTDClassifier.N5ebf2447117(i);
    } else if (((Double) i[1]).doubleValue() > 29.0) {
      p = 1;
    } 
    return p;
  }
  static double N5ebf2447117(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.006370385) {
    p = FECTDClassifier.N56e6e1e9118(i);
    } else if (((Double) i[0]).doubleValue() > 0.006370385) {
      p = 1;
    } 
    return p;
  }
  static double N56e6e1e9118(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 1;
    } else if (((Double) i[2]).doubleValue() <= 0.001449354) {
      p = 1;
    } else if (((Double) i[2]).doubleValue() > 0.001449354) {
      p = 0;
    } 
    return p;
  }
  static double N4ff9b491119(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.006481742) {
    p = FECTDClassifier.N12bdc49c120(i);
    } else if (((Double) i[0]).doubleValue() > 0.006481742) {
    p = FECTDClassifier.N5392341b129(i);
    } 
    return p;
  }
  static double N12bdc49c120(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 0;
    } else if (((Double) i[4]).doubleValue() <= 0.501565182) {
    p = FECTDClassifier.N68b6e760121(i);
    } else if (((Double) i[4]).doubleValue() > 0.501565182) {
      p = 0;
    } 
    return p;
  }
  static double N68b6e760121(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 70.0) {
    p = FECTDClassifier.N40108555122(i);
    } else if (((Double) i[1]).doubleValue() > 70.0) {
    p = FECTDClassifier.N131a212b127(i);
    } 
    return p;
  }
  static double N40108555122(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.464767337) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 0.464767337) {
    p = FECTDClassifier.N7ca1b04e123(i);
    } 
    return p;
  }
  static double N7ca1b04e123(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 35.0) {
    p = FECTDClassifier.N5b3e10ce124(i);
    } else if (((Double) i[1]).doubleValue() > 35.0) {
    p = FECTDClassifier.N47c1732e125(i);
    } 
    return p;
  }
  static double N5b3e10ce124(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.00580112) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 0.00580112) {
      p = 1;
    } 
    return p;
  }
  static double N47c1732e125(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 1;
    } else if (((Double) i[3]).doubleValue() <= 0.512555778) {
    p = FECTDClassifier.N6d70f5cf126(i);
    } else if (((Double) i[3]).doubleValue() > 0.512555778) {
      p = 1;
    } 
    return p;
  }
  static double N6d70f5cf126(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 1;
    } else if (((Double) i[3]).doubleValue() <= 0.495871335) {
      p = 1;
    } else if (((Double) i[3]).doubleValue() > 0.495871335) {
      p = 0;
    } 
    return p;
  }
  static double N131a212b127(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.00510935) {
    p = FECTDClassifier.N187797d9128(i);
    } else if (((Double) i[0]).doubleValue() > 0.00510935) {
      p = 1;
    } 
    return p;
  }
  static double N187797d9128(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 1;
    } else if (((Double) i[2]).doubleValue() <= 0.001835451) {
      p = 1;
    } else if (((Double) i[2]).doubleValue() > 0.001835451) {
      p = 0;
    } 
    return p;
  }
  static double N5392341b129(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 1;
    } else if (((Double) i[1]).doubleValue() <= 39.0) {
    p = FECTDClassifier.N239b037a130(i);
    } else if (((Double) i[1]).doubleValue() > 39.0) {
    p = FECTDClassifier.N9862830135(i);
    } 
    return p;
  }
  static double N239b037a130(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.405620337) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 0.405620337) {
    p = FECTDClassifier.N160150d9131(i);
    } 
    return p;
  }
  static double N160150d9131(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.527550533) {
    p = FECTDClassifier.N3062e1d8132(i);
    } else if (((Double) i[4]).doubleValue() > 0.527550533) {
    p = FECTDClassifier.N452ee42c134(i);
    } 
    return p;
  }
  static double N3062e1d8132(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.007023475) {
    p = FECTDClassifier.N2bd944b9133(i);
    } else if (((Double) i[0]).doubleValue() > 0.007023475) {
      p = 1;
    } 
    return p;
  }
  static double N2bd944b9133(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.484256029) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 0.484256029) {
      p = 1;
    } 
    return p;
  }
  static double N452ee42c134(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 0;
    } else if (((Double) i[2]).doubleValue() <= 0.00489907) {
      p = 0;
    } else if (((Double) i[2]).doubleValue() > 0.00489907) {
      p = 1;
    } 
    return p;
  }
  static double N9862830135(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 1;
    } else if (((Double) i[3]).doubleValue() <= 0.4053289) {
    p = FECTDClassifier.N46b86c32136(i);
    } else if (((Double) i[3]).doubleValue() > 0.4053289) {
      p = 1;
    } 
    return p;
  }
  static double N46b86c32136(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.498883713) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() > 0.498883713) {
      p = 0;
    } 
    return p;
  }
  static double N75c76ae3137(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 1;
    } else if (((Double) i[1]).doubleValue() <= 15.0) {
    p = FECTDClassifier.N773a997d138(i);
    } else if (((Double) i[1]).doubleValue() > 15.0) {
      p = 1;
    } 
    return p;
  }
  static double N773a997d138(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.452432558) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() > 0.452432558) {
    p = FECTDClassifier.N28331ab2139(i);
    } 
    return p;
  }
  static double N28331ab2139(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.007995586) {
    p = FECTDClassifier.N331db0bc140(i);
    } else if (((Double) i[0]).doubleValue() > 0.007995586) {
      p = 1;
    } 
    return p;
  }
  static double N331db0bc140(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 1;
    } else if (((Double) i[1]).doubleValue() <= 14.0) {
    p = FECTDClassifier.N623e20db141(i);
    } else if (((Double) i[1]).doubleValue() > 14.0) {
      p = 0;
    } 
    return p;
  }
  static double N623e20db141(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.51661255) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() > 0.51661255) {
      p = 0;
    } 
    return p;
  }
  static double N5cdf3c40142(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 0;
    } else if (((Double) i[4]).doubleValue() <= 0.750030548) {
    p = FECTDClassifier.N43ecbd62143(i);
    } else if (((Double) i[4]).doubleValue() > 0.750030548) {
    p = FECTDClassifier.N56bf19cc173(i);
    } 
    return p;
  }
  static double N43ecbd62143(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.006372392) {
    p = FECTDClassifier.N6b858fc4144(i);
    } else if (((Double) i[0]).doubleValue() > 0.006372392) {
    p = FECTDClassifier.N1be4aa68152(i);
    } 
    return p;
  }
  static double N6b858fc4144(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 0;
    } else if (((Double) i[4]).doubleValue() <= 0.65768102) {
    p = FECTDClassifier.Nda1c402145(i);
    } else if (((Double) i[4]).doubleValue() > 0.65768102) {
      p = 0;
    } 
    return p;
  }
  static double Nda1c402145(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.782130003) {
    p = FECTDClassifier.N774b664b146(i);
    } else if (((Double) i[3]).doubleValue() > 0.782130003) {
    p = FECTDClassifier.N6164d52f149(i);
    } 
    return p;
  }
  static double N774b664b146(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.617869854) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 0.617869854) {
    p = FECTDClassifier.N7729031c147(i);
    } 
    return p;
  }
  static double N7729031c147(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.005968098) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 0.005968098) {
    p = FECTDClassifier.N258b6062148(i);
    } 
    return p;
  }
  static double N258b6062148(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 1;
    } else if (((Double) i[0]).doubleValue() <= 0.006267055) {
      p = 1;
    } else if (((Double) i[0]).doubleValue() > 0.006267055) {
      p = 0;
    } 
    return p;
  }
  static double N6164d52f149(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.005259831) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 0.005259831) {
    p = FECTDClassifier.N22e8329d150(i);
    } 
    return p;
  }
  static double N22e8329d150(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.637701901) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() > 0.637701901) {
    p = FECTDClassifier.N3a52f352151(i);
    } 
    return p;
  }
  static double N3a52f352151(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.005940008) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 0.005940008) {
      p = 1;
    } 
    return p;
  }
  static double N1be4aa68152(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.589765787) {
    p = FECTDClassifier.N416fa026153(i);
    } else if (((Double) i[3]).doubleValue() > 0.589765787) {
    p = FECTDClassifier.N43b9ef10164(i);
    } 
    return p;
  }
  static double N416fa026153(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 28.0) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() > 28.0) {
    p = FECTDClassifier.N77b405a154(i);
    } 
    return p;
  }
  static double N77b405a154(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.690626029) {
    p = FECTDClassifier.N2cbdd88c155(i);
    } else if (((Double) i[4]).doubleValue() > 0.690626029) {
    p = FECTDClassifier.N1323359f162(i);
    } 
    return p;
  }
  static double N2cbdd88c155(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.008041992) {
    p = FECTDClassifier.N63d3e646156(i);
    } else if (((Double) i[0]).doubleValue() > 0.008041992) {
    p = FECTDClassifier.N24cd65cc159(i);
    } 
    return p;
  }
  static double N63d3e646156(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 0;
    } else if (((Double) i[4]).doubleValue() <= 0.635541818) {
    p = FECTDClassifier.N6cb330dd157(i);
    } else if (((Double) i[4]).doubleValue() > 0.635541818) {
      p = 0;
    } 
    return p;
  }
  static double N6cb330dd157(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.487668782) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 0.487668782) {
    p = FECTDClassifier.N684d34eb158(i);
    } 
    return p;
  }
  static double N684d34eb158(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.006688403) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 0.006688403) {
      p = 1;
    } 
    return p;
  }
  static double N24cd65cc159(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.466920108) {
    p = FECTDClassifier.N28d646f4160(i);
    } else if (((Double) i[3]).doubleValue() > 0.466920108) {
      p = 1;
    } 
    return p;
  }
  static double N28d646f4160(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 0;
    } else if (((Double) i[2]).doubleValue() <= 0.006069305) {
    p = FECTDClassifier.Nbcc521e161(i);
    } else if (((Double) i[2]).doubleValue() > 0.006069305) {
      p = 1;
    } 
    return p;
  }
  static double Nbcc521e161(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.613820288) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() > 0.613820288) {
      p = 0;
    } 
    return p;
  }
  static double N1323359f162(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 0;
    } else if (((Double) i[2]).doubleValue() <= 0.006883404) {
      p = 0;
    } else if (((Double) i[2]).doubleValue() > 0.006883404) {
    p = FECTDClassifier.N6c956389163(i);
    } 
    return p;
  }
  static double N6c956389163(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 1;
    } else if (((Double) i[0]).doubleValue() <= 0.009492376) {
      p = 1;
    } else if (((Double) i[0]).doubleValue() > 0.009492376) {
      p = 0;
    } 
    return p;
  }
  static double N43b9ef10164(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.698366664) {
    p = FECTDClassifier.N63fe262c165(i);
    } else if (((Double) i[4]).doubleValue() > 0.698366664) {
    p = FECTDClassifier.N6cd70724167(i);
    } 
    return p;
  }
  static double N63fe262c165(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 1;
    } else if (((Double) i[1]).doubleValue() <= 24.0) {
    p = FECTDClassifier.N427c45fd166(i);
    } else if (((Double) i[1]).doubleValue() > 24.0) {
      p = 1;
    } 
    return p;
  }
  static double N427c45fd166(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 0;
    } else if (((Double) i[2]).doubleValue() <= 0.004654643) {
      p = 0;
    } else if (((Double) i[2]).doubleValue() > 0.004654643) {
      p = 1;
    } 
    return p;
  }
  static double N6cd70724167(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.007877207) {
    p = FECTDClassifier.N1916044f168(i);
    } else if (((Double) i[0]).doubleValue() > 0.007877207) {
    p = FECTDClassifier.N280ab145170(i);
    } 
    return p;
  }
  static double N1916044f168(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 0;
    } else if (((Double) i[4]).doubleValue() <= 0.704507533) {
    p = FECTDClassifier.N7474eb66169(i);
    } else if (((Double) i[4]).doubleValue() > 0.704507533) {
      p = 0;
    } 
    return p;
  }
  static double N7474eb66169(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.007229133) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 0.007229133) {
      p = 1;
    } 
    return p;
  }
  static double N280ab145170(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 1;
    } else if (((Double) i[1]).doubleValue() <= 39.0) {
    p = FECTDClassifier.N55fc3d8c171(i);
    } else if (((Double) i[1]).doubleValue() > 39.0) {
      p = 1;
    } 
    return p;
  }
  static double N55fc3d8c171(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 24.0) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() > 24.0) {
    p = FECTDClassifier.N232cde6e172(i);
    } 
    return p;
  }
  static double N232cde6e172(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.008160112) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 0.008160112) {
      p = 1;
    } 
    return p;
  }
  static double N56bf19cc173(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.008695827) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 0.008695827) {
    p = FECTDClassifier.N1c36cc92174(i);
    } 
    return p;
  }
  static double N1c36cc92174(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 0;
    } else if (((Double) i[4]).doubleValue() <= 0.798529807) {
    p = FECTDClassifier.N51b093b6175(i);
    } else if (((Double) i[4]).doubleValue() > 0.798529807) {
      p = 0;
    } 
    return p;
  }
  static double N51b093b6175(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.678276539) {
    p = FECTDClassifier.N1fb1bba0176(i);
    } else if (((Double) i[3]).doubleValue() > 0.678276539) {
    p = FECTDClassifier.N76bf24d1178(i);
    } 
    return p;
  }
  static double N1fb1bba0176(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 0;
    } else if (((Double) i[4]).doubleValue() <= 0.75418499) {
    p = FECTDClassifier.N4d9515a1177(i);
    } else if (((Double) i[4]).doubleValue() > 0.75418499) {
      p = 0;
    } 
    return p;
  }
  static double N4d9515a1177(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 0;
    } else if (((Double) i[4]).doubleValue() <= 0.752044024) {
      p = 0;
    } else if (((Double) i[4]).doubleValue() > 0.752044024) {
      p = 1;
    } 
    return p;
  }
  static double N76bf24d1178(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.769024037) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() > 0.769024037) {
    p = FECTDClassifier.N7f0a523e179(i);
    } 
    return p;
  }
  static double N7f0a523e179(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 65.0) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() > 65.0) {
    p = FECTDClassifier.N7e9da59a180(i);
    } 
    return p;
  }
  static double N7e9da59a180(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 0;
    } else if (((Double) i[2]).doubleValue() <= 0.007205056) {
      p = 0;
    } else if (((Double) i[2]).doubleValue() > 0.007205056) {
      p = 1;
    } 
    return p;
  }
  static double N1fe36267181(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 1;
    } else if (((Double) i[3]).doubleValue() <= 0.313472688) {
    p = FECTDClassifier.N951688c182(i);
    } else if (((Double) i[3]).doubleValue() > 0.313472688) {
    p = FECTDClassifier.N4e72a3c2280(i);
    } 
    return p;
  }
  static double N951688c182(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.024829454) {
    p = FECTDClassifier.N3fa6c41b183(i);
    } else if (((Double) i[0]).doubleValue() > 0.024829454) {
    p = FECTDClassifier.Ne93804b230(i);
    } 
    return p;
  }
  static double N3fa6c41b183(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.465320635) {
    p = FECTDClassifier.N5d94e942184(i);
    } else if (((Double) i[4]).doubleValue() > 0.465320635) {
    p = FECTDClassifier.N4c268750206(i);
    } 
    return p;
  }
  static double N5d94e942184(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.01655297) {
    p = FECTDClassifier.N5b5e1c0d185(i);
    } else if (((Double) i[0]).doubleValue() > 0.01655297) {
    p = FECTDClassifier.N74159ff4198(i);
    } 
    return p;
  }
  static double N5b5e1c0d185(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.255758464) {
    p = FECTDClassifier.N7f83c857186(i);
    } else if (((Double) i[3]).doubleValue() > 0.255758464) {
    p = FECTDClassifier.N5ff333fc188(i);
    } 
    return p;
  }
  static double N7f83c857186(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 0;
    } else if (((Double) i[4]).doubleValue() <= 0.186787042) {
    p = FECTDClassifier.N24d60928187(i);
    } else if (((Double) i[4]).doubleValue() > 0.186787042) {
      p = 0;
    } 
    return p;
  }
  static double N24d60928187(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 0;
    } else if (((Double) i[2]).doubleValue() <= 0.002446321) {
      p = 0;
    } else if (((Double) i[2]).doubleValue() > 0.002446321) {
      p = 1;
    } 
    return p;
  }
  static double N5ff333fc188(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.260942483) {
    p = FECTDClassifier.N57da169a189(i);
    } else if (((Double) i[4]).doubleValue() > 0.260942483) {
    p = FECTDClassifier.N298441ec192(i);
    } 
    return p;
  }
  static double N57da169a189(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 1;
    } else if (((Double) i[0]).doubleValue() <= 0.011040329) {
    p = FECTDClassifier.N2f020585190(i);
    } else if (((Double) i[0]).doubleValue() > 0.011040329) {
      p = 1;
    } 
    return p;
  }
  static double N2f020585190(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 60.0) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() > 60.0) {
    p = FECTDClassifier.N2db876df191(i);
    } 
    return p;
  }
  static double N2db876df191(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.265731275) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 0.265731275) {
      p = 1;
    } 
    return p;
  }
  static double N298441ec192(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 70.0) {
    p = FECTDClassifier.N2a04023f193(i);
    } else if (((Double) i[1]).doubleValue() > 70.0) {
    p = FECTDClassifier.N4c7935a3195(i);
    } 
    return p;
  }
  static double N2a04023f193(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 0;
    } else if (((Double) i[4]).doubleValue() <= 0.309244816) {
    p = FECTDClassifier.N6d2f8ba5194(i);
    } else if (((Double) i[4]).doubleValue() > 0.309244816) {
      p = 0;
    } 
    return p;
  }
  static double N6d2f8ba5194(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.014164326) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 0.014164326) {
      p = 1;
    } 
    return p;
  }
  static double N4c7935a3195(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.01174057) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 0.01174057) {
    p = FECTDClassifier.N29b0878e196(i);
    } 
    return p;
  }
  static double N29b0878e196(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.272064298) {
    p = FECTDClassifier.N4939104197(i);
    } else if (((Double) i[3]).doubleValue() > 0.272064298) {
      p = 1;
    } 
    return p;
  }
  static double N4939104197(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.014261637) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 0.014261637) {
      p = 1;
    } 
    return p;
  }
  static double N74159ff4198(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.240971118) {
    p = FECTDClassifier.N37b887b6199(i);
    } else if (((Double) i[3]).doubleValue() > 0.240971118) {
      p = 1;
    } 
    return p;
  }
  static double N37b887b6199(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 57.0) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() > 57.0) {
    p = FECTDClassifier.N33d5da4e200(i);
    } 
    return p;
  }
  static double N33d5da4e200(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.020962079) {
    p = FECTDClassifier.N1cef5178201(i);
    } else if (((Double) i[0]).doubleValue() > 0.020962079) {
    p = FECTDClassifier.N243db10b204(i);
    } 
    return p;
  }
  static double N1cef5178201(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 1;
    } else if (((Double) i[2]).doubleValue() <= 0.004248744) {
      p = 1;
    } else if (((Double) i[2]).doubleValue() > 0.004248744) {
    p = FECTDClassifier.N23cdac1f202(i);
    } 
    return p;
  }
  static double N23cdac1f202(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.231604502) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 0.231604502) {
    p = FECTDClassifier.N13df3996203(i);
    } 
    return p;
  }
  static double N13df3996203(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.333579262) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() > 0.333579262) {
      p = 0;
    } 
    return p;
  }
  static double N243db10b204(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 1;
    } else if (((Double) i[2]).doubleValue() <= 0.008172749) {
      p = 1;
    } else if (((Double) i[2]).doubleValue() > 0.008172749) {
    p = FECTDClassifier.N4e325bc3205(i);
    } 
    return p;
  }
  static double N4e325bc3205(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 73.0) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() > 73.0) {
      p = 1;
    } 
    return p;
  }
  static double N4c268750206(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.26092124) {
    p = FECTDClassifier.N757dbc3e207(i);
    } else if (((Double) i[3]).doubleValue() > 0.26092124) {
    p = FECTDClassifier.N49e05810212(i);
    } 
    return p;
  }
  static double N757dbc3e207(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 0;
    } else if (((Double) i[4]).doubleValue() <= 0.595000776) {
    p = FECTDClassifier.N11d1c6b5208(i);
    } else if (((Double) i[4]).doubleValue() > 0.595000776) {
      p = 0;
    } 
    return p;
  }
  static double N11d1c6b5208(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.018713884) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 0.018713884) {
    p = FECTDClassifier.N634c9e36209(i);
    } 
    return p;
  }
  static double N634c9e36209(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 66.0) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() > 66.0) {
    p = FECTDClassifier.N3b271e28210(i);
    } 
    return p;
  }
  static double N3b271e28210(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.241367728) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 0.241367728) {
    p = FECTDClassifier.N52cf26f211(i);
    } 
    return p;
  }
  static double N52cf26f211(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.552150537) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() > 0.552150537) {
      p = 0;
    } 
    return p;
  }
  static double N49e05810212(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 0;
    } else if (((Double) i[4]).doubleValue() <= 0.607619445) {
    p = FECTDClassifier.N27b5a854213(i);
    } else if (((Double) i[4]).doubleValue() > 0.607619445) {
    p = FECTDClassifier.N826dc2d220(i);
    } 
    return p;
  }
  static double N27b5a854213(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.015526685) {
    p = FECTDClassifier.N73e372a214(i);
    } else if (((Double) i[0]).doubleValue() > 0.015526685) {
    p = FECTDClassifier.N254c08b4217(i);
    } 
    return p;
  }
  static double N73e372a214(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.304298729) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 0.304298729) {
    p = FECTDClassifier.N597aa1d215(i);
    } 
    return p;
  }
  static double N597aa1d215(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.011731541) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 0.011731541) {
    p = FECTDClassifier.N282158c9216(i);
    } 
    return p;
  }
  static double N282158c9216(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 69.0) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() > 69.0) {
      p = 1;
    } 
    return p;
  }
  static double N254c08b4217(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 43.0) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() > 43.0) {
    p = FECTDClassifier.N22cf748d218(i);
    } 
    return p;
  }
  static double N22cf748d218(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 1;
    } else if (((Double) i[0]).doubleValue() <= 0.017074913) {
    p = FECTDClassifier.N61ece6d5219(i);
    } else if (((Double) i[0]).doubleValue() > 0.017074913) {
      p = 1;
    } 
    return p;
  }
  static double N61ece6d5219(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 89.0) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() > 89.0) {
      p = 1;
    } 
    return p;
  }
  static double N826dc2d220(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.019241573) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 0.019241573) {
    p = FECTDClassifier.N2f411289221(i);
    } 
    return p;
  }
  static double N2f411289221(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 61.0) {
    p = FECTDClassifier.N5927f89b222(i);
    } else if (((Double) i[1]).doubleValue() > 61.0) {
    p = FECTDClassifier.Na405ee0224(i);
    } 
    return p;
  }
  static double N5927f89b222(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.024552568) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 0.024552568) {
    p = FECTDClassifier.N4f32b5d7223(i);
    } 
    return p;
  }
  static double N4f32b5d7223(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 1;
    } else if (((Double) i[2]).doubleValue() <= 0.017670123) {
      p = 1;
    } else if (((Double) i[2]).doubleValue() > 0.017670123) {
      p = 0;
    } 
    return p;
  }
  static double Na405ee0224(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.743676212) {
    p = FECTDClassifier.N814c962225(i);
    } else if (((Double) i[4]).doubleValue() > 0.743676212) {
    p = FECTDClassifier.N66d87b7d229(i);
    } 
    return p;
  }
  static double N814c962225(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 1;
    } else if (((Double) i[3]).doubleValue() <= 0.28404367) {
    p = FECTDClassifier.Ncb14513226(i);
    } else if (((Double) i[3]).doubleValue() > 0.28404367) {
      p = 1;
    } 
    return p;
  }
  static double Ncb14513226(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 73.0) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() > 73.0) {
    p = FECTDClassifier.N4a2de8e7227(i);
    } 
    return p;
  }
  static double N4a2de8e7227(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.670088713) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() > 0.670088713) {
    p = FECTDClassifier.Nc15bbbd228(i);
    } 
    return p;
  }
  static double Nc15bbbd228(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 0;
    } else if (((Double) i[4]).doubleValue() <= 0.699466687) {
      p = 0;
    } else if (((Double) i[4]).doubleValue() > 0.699466687) {
      p = 1;
    } 
    return p;
  }
  static double N66d87b7d229(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 103.0) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() > 103.0) {
      p = 1;
    } 
    return p;
  }
  static double Ne93804b230(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 1;
    } else if (((Double) i[1]).doubleValue() <= 41.0) {
    p = FECTDClassifier.N75cbc366231(i);
    } else if (((Double) i[1]).doubleValue() > 41.0) {
    p = FECTDClassifier.N1bb0438a253(i);
    } 
    return p;
  }
  static double N75cbc366231(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.259678721) {
    p = FECTDClassifier.N148999f5232(i);
    } else if (((Double) i[3]).doubleValue() > 0.259678721) {
    p = FECTDClassifier.N22c9be5f241(i);
    } 
    return p;
  }
  static double N148999f5232(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.039122191) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 0.039122191) {
    p = FECTDClassifier.N55daae5b233(i);
    } 
    return p;
  }
  static double N55daae5b233(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 1;
    } else if (((Double) i[2]).doubleValue() <= 0.025227858) {
      p = 1;
    } else if (((Double) i[2]).doubleValue() > 0.025227858) {
    p = FECTDClassifier.N7ed0466234(i);
    } 
    return p;
  }
  static double N7ed0466234(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 34.0) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() > 34.0) {
    p = FECTDClassifier.N59bbc89a235(i);
    } 
    return p;
  }
  static double N59bbc89a235(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.239344284) {
    p = FECTDClassifier.N376f247c236(i);
    } else if (((Double) i[3]).doubleValue() > 0.239344284) {
    p = FECTDClassifier.N6840243a239(i);
    } 
    return p;
  }
  static double N376f247c236(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 0;
    } else if (((Double) i[2]).doubleValue() <= 0.037996326) {
    p = FECTDClassifier.N61c46552237(i);
    } else if (((Double) i[2]).doubleValue() > 0.037996326) {
      p = 1;
    } 
    return p;
  }
  static double N61c46552237(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.230558753) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 0.230558753) {
    p = FECTDClassifier.N24d81ca3238(i);
    } 
    return p;
  }
  static double N24d81ca3238(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 39.0) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() > 39.0) {
      p = 1;
    } 
    return p;
  }
  static double N6840243a239(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 1;
    } else if (((Double) i[1]).doubleValue() <= 36.0) {
    p = FECTDClassifier.N4b0a914e240(i);
    } else if (((Double) i[1]).doubleValue() > 36.0) {
      p = 1;
    } 
    return p;
  }
  static double N4b0a914e240(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 1;
    } else if (((Double) i[2]).doubleValue() <= 0.033529643) {
      p = 1;
    } else if (((Double) i[2]).doubleValue() > 0.033529643) {
      p = 0;
    } 
    return p;
  }
  static double N22c9be5f241(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 30.0) {
    p = FECTDClassifier.N6af160d0242(i);
    } else if (((Double) i[1]).doubleValue() > 30.0) {
    p = FECTDClassifier.N354f49c8248(i);
    } 
    return p;
  }
  static double N6af160d0242(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.03114065) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 0.03114065) {
    p = FECTDClassifier.Nd0b2e8a243(i);
    } 
    return p;
  }
  static double Nd0b2e8a243(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.276233763) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 0.276233763) {
    p = FECTDClassifier.N591c6cb6244(i);
    } 
    return p;
  }
  static double N591c6cb6244(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 1;
    } else if (((Double) i[2]).doubleValue() <= 0.033408415) {
    p = FECTDClassifier.N59254e6e245(i);
    } else if (((Double) i[2]).doubleValue() > 0.033408415) {
      p = 0;
    } 
    return p;
  }
  static double N59254e6e245(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.824362052) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() > 0.824362052) {
    p = FECTDClassifier.N2040457b246(i);
    } 
    return p;
  }
  static double N2040457b246(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.298752248) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 0.298752248) {
    p = FECTDClassifier.N5b919ec7247(i);
    } 
    return p;
  }
  static double N5b919ec7247(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 26.0) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() > 26.0) {
      p = 1;
    } 
    return p;
  }
  static double N354f49c8248(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 1;
    } else if (((Double) i[0]).doubleValue() <= 0.033089062) {
    p = FECTDClassifier.N687504cf249(i);
    } else if (((Double) i[0]).doubleValue() > 0.033089062) {
      p = 1;
    } 
    return p;
  }
  static double N687504cf249(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.290486515) {
    p = FECTDClassifier.N5a8ee79c250(i);
    } else if (((Double) i[3]).doubleValue() > 0.290486515) {
      p = 1;
    } 
    return p;
  }
  static double N5a8ee79c250(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.554213043) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() > 0.554213043) {
    p = FECTDClassifier.N5c07e136251(i);
    } 
    return p;
  }
  static double N5c07e136251(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 0;
    } else if (((Double) i[4]).doubleValue() <= 0.692772727) {
    p = FECTDClassifier.N952cf6e252(i);
    } else if (((Double) i[4]).doubleValue() > 0.692772727) {
      p = 0;
    } 
    return p;
  }
  static double N952cf6e252(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.279397756) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 0.279397756) {
      p = 1;
    } 
    return p;
  }
  static double N1bb0438a253(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 1;
    } else if (((Double) i[0]).doubleValue() <= 0.036533909) {
    p = FECTDClassifier.N51222739254(i);
    } else if (((Double) i[0]).doubleValue() > 0.036533909) {
    p = FECTDClassifier.N53d06d9b277(i);
    } 
    return p;
  }
  static double N51222739254(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.594716319) {
    p = FECTDClassifier.N193d38cc255(i);
    } else if (((Double) i[4]).doubleValue() > 0.594716319) {
    p = FECTDClassifier.N6e84522c259(i);
    } 
    return p;
  }
  static double N193d38cc255(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 1;
    } else if (((Double) i[1]).doubleValue() <= 56.0) {
    p = FECTDClassifier.N25be606256(i);
    } else if (((Double) i[1]).doubleValue() > 56.0) {
      p = 1;
    } 
    return p;
  }
  static double N25be606256(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 1;
    } else if (((Double) i[3]).doubleValue() <= 0.245217532) {
    p = FECTDClassifier.N5f5a951f257(i);
    } else if (((Double) i[3]).doubleValue() > 0.245217532) {
      p = 1;
    } 
    return p;
  }
  static double N5f5a951f257(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.028578481) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 0.028578481) {
    p = FECTDClassifier.N33f45721258(i);
    } 
    return p;
  }
  static double N33f45721258(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.226421684) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 0.226421684) {
      p = 1;
    } 
    return p;
  }
  static double N6e84522c259(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.254181623) {
    p = FECTDClassifier.N312eff63260(i);
    } else if (((Double) i[3]).doubleValue() > 0.254181623) {
    p = FECTDClassifier.N4e9208e1269(i);
    } 
    return p;
  }
  static double N312eff63260(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 61.0) {
    p = FECTDClassifier.N480d5cf261(i);
    } else if (((Double) i[1]).doubleValue() > 61.0) {
    p = FECTDClassifier.N5b3c6718264(i);
    } 
    return p;
  }
  static double N480d5cf261(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.033548967) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 0.033548967) {
    p = FECTDClassifier.N26550b58262(i);
    } 
    return p;
  }
  static double N26550b58262(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.240959138) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 0.240959138) {
    p = FECTDClassifier.N195bd211263(i);
    } 
    return p;
  }
  static double N195bd211263(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.737806494) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() > 0.737806494) {
      p = 0;
    } 
    return p;
  }
  static double N5b3c6718264(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.031265048) {
    p = FECTDClassifier.N5a948773265(i);
    } else if (((Double) i[0]).doubleValue() > 0.031265048) {
    p = FECTDClassifier.N628fbc39267(i);
    } 
    return p;
  }
  static double N5a948773265(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.685124342) {
    p = FECTDClassifier.N4d48bd7a266(i);
    } else if (((Double) i[4]).doubleValue() > 0.685124342) {
      p = 0;
    } 
    return p;
  }
  static double N4d48bd7a266(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 73.0) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() > 73.0) {
      p = 1;
    } 
    return p;
  }
  static double N628fbc39267(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.740916339) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() > 0.740916339) {
    p = FECTDClassifier.N4a8e74bc268(i);
    } 
    return p;
  }
  static double N4a8e74bc268(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.239176542) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 0.239176542) {
      p = 1;
    } 
    return p;
  }
  static double N4e9208e1269(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 1;
    } else if (((Double) i[0]).doubleValue() <= 0.029892439) {
    p = FECTDClassifier.N55851413270(i);
    } else if (((Double) i[0]).doubleValue() > 0.029892439) {
      p = 1;
    } 
    return p;
  }
  static double N55851413270(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.779620521) {
    p = FECTDClassifier.N13e91742271(i);
    } else if (((Double) i[4]).doubleValue() > 0.779620521) {
    p = FECTDClassifier.N2020b0dd275(i);
    } 
    return p;
  }
  static double N13e91742271(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 1;
    } else if (((Double) i[3]).doubleValue() <= 0.272802979) {
    p = FECTDClassifier.N2bf5f844272(i);
    } else if (((Double) i[3]).doubleValue() > 0.272802979) {
      p = 1;
    } 
    return p;
  }
  static double N2bf5f844272(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 62.0) {
    p = FECTDClassifier.N217e4ee8273(i);
    } else if (((Double) i[1]).doubleValue() > 62.0) {
      p = 1;
    } 
    return p;
  }
  static double N217e4ee8273(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.027742777) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 0.027742777) {
    p = FECTDClassifier.N6b6e7285274(i);
    } 
    return p;
  }
  static double N6b6e7285274(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 51.0) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() > 51.0) {
      p = 1;
    } 
    return p;
  }
  static double N2020b0dd275(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.284584343) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 0.284584343) {
    p = FECTDClassifier.N423b8da5276(i);
    } 
    return p;
  }
  static double N423b8da5276(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.832019481) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() > 0.832019481) {
      p = 0;
    } 
    return p;
  }
  static double N53d06d9b277(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 1;
    } else if (((Double) i[3]).doubleValue() <= 0.231062576) {
    p = FECTDClassifier.N18cc061a278(i);
    } else if (((Double) i[3]).doubleValue() > 0.231062576) {
      p = 1;
    } 
    return p;
  }
  static double N18cc061a278(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 1;
    } else if (((Double) i[1]).doubleValue() <= 46.0) {
    p = FECTDClassifier.N7aa4a1ad279(i);
    } else if (((Double) i[1]).doubleValue() > 46.0) {
      p = 1;
    } 
    return p;
  }
  static double N7aa4a1ad279(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.042278291) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 0.042278291) {
      p = 1;
    } 
    return p;
  }
  static double N4e72a3c2280(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.747770378) {
    p = FECTDClassifier.N485d3dca281(i);
    } else if (((Double) i[4]).doubleValue() > 0.747770378) {
    p = FECTDClassifier.N199d2c48313(i);
    } 
    return p;
  }
  static double N485d3dca281(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 1;
    } else if (((Double) i[3]).doubleValue() <= 0.396113813) {
    p = FECTDClassifier.N6183bde3282(i);
    } else if (((Double) i[3]).doubleValue() > 0.396113813) {
    p = FECTDClassifier.N7c354968308(i);
    } 
    return p;
  }
  static double N6183bde3282(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 1;
    } else if (((Double) i[0]).doubleValue() <= 0.019657905) {
    p = FECTDClassifier.N1027b419283(i);
    } else if (((Double) i[0]).doubleValue() > 0.019657905) {
    p = FECTDClassifier.N592a6c02300(i);
    } 
    return p;
  }
  static double N1027b419283(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.601125436) {
    p = FECTDClassifier.N1ea0dd98284(i);
    } else if (((Double) i[4]).doubleValue() > 0.601125436) {
    p = FECTDClassifier.N780e2719291(i);
    } 
    return p;
  }
  static double N1ea0dd98284(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 1;
    } else if (((Double) i[1]).doubleValue() <= 34.0) {
    p = FECTDClassifier.N533435dd285(i);
    } else if (((Double) i[1]).doubleValue() > 34.0) {
    p = FECTDClassifier.Na0a8f63287(i);
    } 
    return p;
  }
  static double N533435dd285(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.012619382) {
    p = FECTDClassifier.N8bc6ad8286(i);
    } else if (((Double) i[0]).doubleValue() > 0.012619382) {
      p = 1;
    } 
    return p;
  }
  static double N8bc6ad8286(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 1;
    } else if (((Double) i[2]).doubleValue() <= 0.004253372) {
      p = 1;
    } else if (((Double) i[2]).doubleValue() > 0.004253372) {
      p = 0;
    } 
    return p;
  }
  static double Na0a8f63287(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 1;
    } else if (((Double) i[0]).doubleValue() <= 0.011761637) {
    p = FECTDClassifier.N3b4bb1bb288(i);
    } else if (((Double) i[0]).doubleValue() > 0.011761637) {
      p = 1;
    } 
    return p;
  }
  static double N3b4bb1bb288(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.385778134) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() > 0.385778134) {
    p = FECTDClassifier.N66818a66289(i);
    } 
    return p;
  }
  static double N66818a66289(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 46.0) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() > 46.0) {
    p = FECTDClassifier.N42a5631d290(i);
    } 
    return p;
  }
  static double N42a5631d290(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.524241908) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() > 0.524241908) {
      p = 0;
    } 
    return p;
  }
  static double N780e2719291(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 49.0) {
    p = FECTDClassifier.N692917e2292(i);
    } else if (((Double) i[1]).doubleValue() > 49.0) {
    p = FECTDClassifier.N61e4cf3d295(i);
    } 
    return p;
  }
  static double N692917e2292(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.015310995) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 0.015310995) {
    p = FECTDClassifier.N8df2c5e293(i);
    } 
    return p;
  }
  static double N8df2c5e293(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 28.0) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() > 28.0) {
    p = FECTDClassifier.N73d9d3de294(i);
    } 
    return p;
  }
  static double N73d9d3de294(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.345232964) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 0.345232964) {
      p = 1;
    } 
    return p;
  }
  static double N61e4cf3d295(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.015093299) {
    p = FECTDClassifier.N74e1e000296(i);
    } else if (((Double) i[0]).doubleValue() > 0.015093299) {
      p = 1;
    } 
    return p;
  }
  static double N74e1e000296(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.332095683) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 0.332095683) {
    p = FECTDClassifier.N19395bf3297(i);
    } 
    return p;
  }
  static double N19395bf3297(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.654392627) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() > 0.654392627) {
    p = FECTDClassifier.N4c3bb75298(i);
    } 
    return p;
  }
  static double N4c3bb75298(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 76.0) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() > 76.0) {
    p = FECTDClassifier.N4e4800c4299(i);
    } 
    return p;
  }
  static double N4e4800c4299(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 1;
    } else if (((Double) i[1]).doubleValue() <= 101.0) {
      p = 1;
    } else if (((Double) i[1]).doubleValue() > 101.0) {
      p = 0;
    } 
    return p;
  }
  static double N592a6c02300(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 1;
    } else if (((Double) i[1]).doubleValue() <= 30.0) {
    p = FECTDClassifier.N70192509301(i);
    } else if (((Double) i[1]).doubleValue() > 30.0) {
      p = 1;
    } 
    return p;
  }
  static double N70192509301(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 1;
    } else if (((Double) i[0]).doubleValue() <= 0.024532513) {
    p = FECTDClassifier.N2ceab5e302(i);
    } else if (((Double) i[0]).doubleValue() > 0.024532513) {
    p = FECTDClassifier.N1cb02395306(i);
    } 
    return p;
  }
  static double N2ceab5e302(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 1;
    } else if (((Double) i[2]).doubleValue() <= 0.012479235) {
      p = 1;
    } else if (((Double) i[2]).doubleValue() > 0.012479235) {
    p = FECTDClassifier.N4e54a9c2303(i);
    } 
    return p;
  }
  static double N4e54a9c2303(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.359626889) {
    p = FECTDClassifier.N185527bb304(i);
    } else if (((Double) i[3]).doubleValue() > 0.359626889) {
      p = 1;
    } 
    return p;
  }
  static double N185527bb304(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.021948234) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 0.021948234) {
    p = FECTDClassifier.N7ea37277305(i);
    } 
    return p;
  }
  static double N7ea37277305(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 1;
    } else if (((Double) i[2]).doubleValue() <= 0.01357661) {
      p = 1;
    } else if (((Double) i[2]).doubleValue() > 0.01357661) {
      p = 0;
    } 
    return p;
  }
  static double N1cb02395306(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 1;
    } else if (((Double) i[2]).doubleValue() <= 0.031776939) {
      p = 1;
    } else if (((Double) i[2]).doubleValue() > 0.031776939) {
    p = FECTDClassifier.N6ff019e9307(i);
    } 
    return p;
  }
  static double N6ff019e9307(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 25.0) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() > 25.0) {
      p = 1;
    } 
    return p;
  }
  static double N7c354968308(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 1;
    } else if (((Double) i[0]).doubleValue() <= 0.014308788) {
    p = FECTDClassifier.Ne668a8d309(i);
    } else if (((Double) i[0]).doubleValue() > 0.014308788) {
      p = 1;
    } 
    return p;
  }
  static double Ne668a8d309(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 1;
    } else if (((Double) i[3]).doubleValue() <= 0.493171781) {
    p = FECTDClassifier.N6e12365d310(i);
    } else if (((Double) i[3]).doubleValue() > 0.493171781) {
      p = 1;
    } 
    return p;
  }
  static double N6e12365d310(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 1;
    } else if (((Double) i[1]).doubleValue() <= 34.0) {
    p = FECTDClassifier.N6daf4c1f311(i);
    } else if (((Double) i[1]).doubleValue() > 34.0) {
      p = 1;
    } 
    return p;
  }
  static double N6daf4c1f311(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.545113601) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() > 0.545113601) {
    p = FECTDClassifier.Nfaebf7b312(i);
    } 
    return p;
  }
  static double Nfaebf7b312(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.013633628) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 0.013633628) {
      p = 1;
    } 
    return p;
  }
  static double N199d2c48313(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 1;
    } else if (((Double) i[0]).doubleValue() <= 0.019542536) {
    p = FECTDClassifier.N1dc6381b314(i);
    } else if (((Double) i[0]).doubleValue() > 0.019542536) {
    p = FECTDClassifier.N32220327353(i);
    } 
    return p;
  }
  static double N1dc6381b314(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.451251209) {
    p = FECTDClassifier.N408d83e2315(i);
    } else if (((Double) i[3]).doubleValue() > 0.451251209) {
    p = FECTDClassifier.Na820ff4326(i);
    } 
    return p;
  }
  static double N408d83e2315(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.014677924) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 0.014677924) {
    p = FECTDClassifier.Nacd8b8a316(i);
    } 
    return p;
  }
  static double Nacd8b8a316(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.415162474) {
    p = FECTDClassifier.N3c841690317(i);
    } else if (((Double) i[3]).doubleValue() > 0.415162474) {
    p = FECTDClassifier.N4e4be661321(i);
    } 
    return p;
  }
  static double N3c841690317(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 0;
    } else if (((Double) i[4]).doubleValue() <= 0.761959088) {
    p = FECTDClassifier.N3e566fa318(i);
    } else if (((Double) i[4]).doubleValue() > 0.761959088) {
    p = FECTDClassifier.N49cba915319(i);
    } 
    return p;
  }
  static double N3e566fa318(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.017682584) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 0.017682584) {
      p = 1;
    } 
    return p;
  }
  static double N49cba915319(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.395957291) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 0.395957291) {
    p = FECTDClassifier.N59c9c78c320(i);
    } 
    return p;
  }
  static double N59c9c78c320(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.796077936) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() > 0.796077936) {
      p = 0;
    } 
    return p;
  }
  static double N4e4be661321(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 42.0) {
    p = FECTDClassifier.N5902126f322(i);
    } else if (((Double) i[1]).doubleValue() > 42.0) {
    p = FECTDClassifier.N279fbb40324(i);
    } 
    return p;
  }
  static double N5902126f322(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.792877644) {
    p = FECTDClassifier.N17086310323(i);
    } else if (((Double) i[4]).doubleValue() > 0.792877644) {
      p = 0;
    } 
    return p;
  }
  static double N17086310323(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 22.0) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() > 22.0) {
      p = 1;
    } 
    return p;
  }
  static double N279fbb40324(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.819033878) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() > 0.819033878) {
    p = FECTDClassifier.N67be7b12325(i);
    } 
    return p;
  }
  static double N67be7b12325(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.017185368) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 0.017185368) {
      p = 1;
    } 
    return p;
  }
  static double Na820ff4326(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.012794944) {
    p = FECTDClassifier.N60e5618f327(i);
    } else if (((Double) i[0]).doubleValue() > 0.012794944) {
    p = FECTDClassifier.N2ca30848341(i);
    } 
    return p;
  }
  static double N60e5618f327(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.806617956) {
    p = FECTDClassifier.N736824fb328(i);
    } else if (((Double) i[4]).doubleValue() > 0.806617956) {
    p = FECTDClassifier.N44019852334(i);
    } 
    return p;
  }
  static double N736824fb328(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.551895857) {
    p = FECTDClassifier.N3a5415ee329(i);
    } else if (((Double) i[3]).doubleValue() > 0.551895857) {
    p = FECTDClassifier.N32c0b33c331(i);
    } 
    return p;
  }
  static double N3a5415ee329(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 0;
    } else if (((Double) i[2]).doubleValue() <= 0.00941544) {
      p = 0;
    } else if (((Double) i[2]).doubleValue() > 0.00941544) {
    p = FECTDClassifier.N666bda2c330(i);
    } 
    return p;
  }
  static double N666bda2c330(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 1;
    } else if (((Double) i[2]).doubleValue() <= 0.009733171) {
      p = 1;
    } else if (((Double) i[2]).doubleValue() > 0.009733171) {
      p = 0;
    } 
    return p;
  }
  static double N32c0b33c331(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 1;
    } else if (((Double) i[1]).doubleValue() <= 38.0) {
    p = FECTDClassifier.N937422c332(i);
    } else if (((Double) i[1]).doubleValue() > 38.0) {
      p = 1;
    } 
    return p;
  }
  static double N937422c332(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.812617004) {
    p = FECTDClassifier.Nad95b6e333(i);
    } else if (((Double) i[3]).doubleValue() > 0.812617004) {
      p = 1;
    } 
    return p;
  }
  static double Nad95b6e333(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 0;
    } else if (((Double) i[2]).doubleValue() <= 0.00929518) {
      p = 0;
    } else if (((Double) i[2]).doubleValue() > 0.00929518) {
      p = 1;
    } 
    return p;
  }
  static double N44019852334(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.870147109) {
    p = FECTDClassifier.N44b7525f335(i);
    } else if (((Double) i[3]).doubleValue() > 0.870147109) {
    p = FECTDClassifier.N409f3aab339(i);
    } 
    return p;
  }
  static double N44b7525f335(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.011867978) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 0.011867978) {
    p = FECTDClassifier.N5f80fe37336(i);
    } 
    return p;
  }
  static double N5f80fe37336(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 0;
    } else if (((Double) i[4]).doubleValue() <= 0.832334017) {
    p = FECTDClassifier.Ndb1fddd337(i);
    } else if (((Double) i[4]).doubleValue() > 0.832334017) {
      p = 0;
    } 
    return p;
  }
  static double Ndb1fddd337(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 27.0) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() > 27.0) {
    p = FECTDClassifier.N2091bf31338(i);
    } 
    return p;
  }
  static double N2091bf31338(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.59985745) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 0.59985745) {
      p = 1;
    } 
    return p;
  }
  static double N409f3aab339(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.838917268) {
    p = FECTDClassifier.N15c4d1b2340(i);
    } else if (((Double) i[4]).doubleValue() > 0.838917268) {
      p = 0;
    } 
    return p;
  }
  static double N15c4d1b2340(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.010654093) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 0.010654093) {
      p = 1;
    } 
    return p;
  }
  static double N2ca30848341(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 1;
    } else if (((Double) i[1]).doubleValue() <= 31.0) {
    p = FECTDClassifier.N374c5dd342(i);
    } else if (((Double) i[1]).doubleValue() > 31.0) {
    p = FECTDClassifier.N3bc22829349(i);
    } 
    return p;
  }
  static double N374c5dd342(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 18.0) {
    p = FECTDClassifier.N676a31f0343(i);
    } else if (((Double) i[1]).doubleValue() > 18.0) {
    p = FECTDClassifier.N4fe1d5ff347(i);
    } 
    return p;
  }
  static double N676a31f0343(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.635455728) {
    p = FECTDClassifier.N6cf4b89a344(i);
    } else if (((Double) i[3]).doubleValue() > 0.635455728) {
      p = 1;
    } 
    return p;
  }
  static double N6cf4b89a344(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.01747191) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 0.01747191) {
    p = FECTDClassifier.N3683be58345(i);
    } 
    return p;
  }
  static double N3683be58345(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.775338357) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() > 0.775338357) {
    p = FECTDClassifier.N3459f5e346(i);
    } 
    return p;
  }
  static double N3459f5e346(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.555795193) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 0.555795193) {
      p = 1;
    } 
    return p;
  }
  static double N4fe1d5ff347(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.527610481) {
    p = FECTDClassifier.N73a88151348(i);
    } else if (((Double) i[3]).doubleValue() > 0.527610481) {
      p = 1;
    } 
    return p;
  }
  static double N73a88151348(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.812677379) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() > 0.812677379) {
      p = 0;
    } 
    return p;
  }
  static double N3bc22829349(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 1;
    } else if (((Double) i[3]).doubleValue() <= 0.518054903) {
    p = FECTDClassifier.N47daba65350(i);
    } else if (((Double) i[3]).doubleValue() > 0.518054903) {
      p = 1;
    } 
    return p;
  }
  static double N47daba65350(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.013538323) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 0.013538323) {
    p = FECTDClassifier.N690361bd351(i);
    } 
    return p;
  }
  static double N690361bd351(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.831857129) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() > 0.831857129) {
    p = FECTDClassifier.N5d05f527352(i);
    } 
    return p;
  }
  static double N5d05f527352(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 0;
    } else if (((Double) i[2]).doubleValue() <= 0.013428818) {
      p = 0;
    } else if (((Double) i[2]).doubleValue() > 0.013428818) {
      p = 1;
    } 
    return p;
  }
  static double N32220327353(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 1;
    } else if (((Double) i[1]).doubleValue() <= 31.0) {
    p = FECTDClassifier.N56fd0f27354(i);
    } else if (((Double) i[1]).doubleValue() > 31.0) {
    p = FECTDClassifier.N26911054360(i);
    } 
    return p;
  }
  static double N56fd0f27354(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 1;
    } else if (((Double) i[3]).doubleValue() <= 0.416543931) {
    p = FECTDClassifier.N7fedf60e355(i);
    } else if (((Double) i[3]).doubleValue() > 0.416543931) {
      p = 1;
    } 
    return p;
  }
  static double N7fedf60e355(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.027993618) {
    p = FECTDClassifier.N5fb552bf356(i);
    } else if (((Double) i[0]).doubleValue() > 0.027993618) {
      p = 1;
    } 
    return p;
  }
  static double N5fb552bf356(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 22.0) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() > 22.0) {
    p = FECTDClassifier.N794baaaf357(i);
    } 
    return p;
  }
  static double N794baaaf357(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.356887192) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 0.356887192) {
    p = FECTDClassifier.N56b3055f358(i);
    } 
    return p;
  }
  static double N56b3055f358(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.023405706) {
    p = FECTDClassifier.N325cc71359(i);
    } else if (((Double) i[0]).doubleValue() > 0.023405706) {
      p = 1;
    } 
    return p;
  }
  static double N325cc71359(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.385318339) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 0.385318339) {
      p = 1;
    } 
    return p;
  }
  static double N26911054360(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 1;
    } else if (((Double) i[3]).doubleValue() <= 0.336600989) {
    p = FECTDClassifier.N7dc70e93361(i);
    } else if (((Double) i[3]).doubleValue() > 0.336600989) {
      p = 1;
    } 
    return p;
  }
  static double N7dc70e93361(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.026822833) {
    p = FECTDClassifier.N178e1568362(i);
    } else if (((Double) i[0]).doubleValue() > 0.026822833) {
      p = 1;
    } 
    return p;
  }
  static double N178e1568362(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.021179775) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 0.021179775) {
    p = FECTDClassifier.N711f6aec363(i);
    } 
    return p;
  }
  static double N711f6aec363(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.812876588) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() > 0.812876588) {
      p = 0;
    } 
    return p;
  }
  static double N45a4e5f9364(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.018728933) {
    p = FECTDClassifier.N4cf66227365(i);
    } else if (((Double) i[0]).doubleValue() > 0.018728933) {
    p = FECTDClassifier.N6f56d4376(i);
    } 
    return p;
  }
  static double N4cf66227365(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.014218499) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 0.014218499) {
    p = FECTDClassifier.N43a615ea366(i);
    } 
    return p;
  }
  static double N43a615ea366(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 0;
    } else if (((Double) i[4]).doubleValue() <= 0.881765676) {
    p = FECTDClassifier.N4ce8d858367(i);
    } else if (((Double) i[4]).doubleValue() > 0.881765676) {
    p = FECTDClassifier.N210b0272373(i);
    } 
    return p;
  }
  static double N4ce8d858367(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.555383623) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 0.555383623) {
    p = FECTDClassifier.N4ad3a0da368(i);
    } 
    return p;
  }
  static double N4ad3a0da368(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.015049157) {
    p = FECTDClassifier.Nbe56e97369(i);
    } else if (((Double) i[0]).doubleValue() > 0.015049157) {
    p = FECTDClassifier.N11d5e909371(i);
    } 
    return p;
  }
  static double Nbe56e97369(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.799596429) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 0.799596429) {
    p = FECTDClassifier.N3bf819b370(i);
    } 
    return p;
  }
  static double N3bf819b370(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.869217667) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() > 0.869217667) {
      p = 0;
    } 
    return p;
  }
  static double N11d5e909371(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 1;
    } else if (((Double) i[1]).doubleValue() <= 41.0) {
    p = FECTDClassifier.N72b65704372(i);
    } else if (((Double) i[1]).doubleValue() > 41.0) {
      p = 1;
    } 
    return p;
  }
  static double N72b65704372(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.706514418) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 0.706514418) {
      p = 1;
    } 
    return p;
  }
  static double N210b0272373(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 0;
    } else if (((Double) i[4]).doubleValue() <= 0.897987601) {
    p = FECTDClassifier.N59cd9b50374(i);
    } else if (((Double) i[4]).doubleValue() > 0.897987601) {
      p = 0;
    } 
    return p;
  }
  static double N59cd9b50374(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.017437358) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 0.017437358) {
    p = FECTDClassifier.N498fcf3f375(i);
    } 
    return p;
  }
  static double N498fcf3f375(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.735265672) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 0.735265672) {
      p = 1;
    } 
    return p;
  }
  static double N6f56d4376(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.918704172) {
    p = FECTDClassifier.Ndad7885377(i);
    } else if (((Double) i[4]).doubleValue() > 0.918704172) {
    p = FECTDClassifier.N486219fa417(i);
    } 
    return p;
  }
  static double Ndad7885377(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 38.0) {
    p = FECTDClassifier.N77c36ac6378(i);
    } else if (((Double) i[1]).doubleValue() > 38.0) {
    p = FECTDClassifier.Nb107f28386(i);
    } 
    return p;
  }
  static double N77c36ac6378(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.340799212) {
    p = FECTDClassifier.N3e972a97379(i);
    } else if (((Double) i[3]).doubleValue() > 0.340799212) {
    p = FECTDClassifier.N3e6af507381(i);
    } 
    return p;
  }
  static double N3e972a97379(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.036467446) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 0.036467446) {
    p = FECTDClassifier.N366d3f9b380(i);
    } 
    return p;
  }
  static double N366d3f9b380(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 34.0) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() > 34.0) {
      p = 1;
    } 
    return p;
  }
  static double N3e6af507381(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 22.0) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() > 22.0) {
    p = FECTDClassifier.N5ffcbe94382(i);
    } 
    return p;
  }
  static double N5ffcbe94382(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.895090843) {
    p = FECTDClassifier.N4a4417c7383(i);
    } else if (((Double) i[4]).doubleValue() > 0.895090843) {
      p = 0;
    } 
    return p;
  }
  static double N4a4417c7383(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 27.0) {
    p = FECTDClassifier.N3c752fe8384(i);
    } else if (((Double) i[1]).doubleValue() > 27.0) {
      p = 1;
    } 
    return p;
  }
  static double N3c752fe8384(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.025974117) {
    p = FECTDClassifier.N319c475a385(i);
    } else if (((Double) i[0]).doubleValue() > 0.025974117) {
      p = 1;
    } 
    return p;
  }
  static double N319c475a385(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.44091627) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 0.44091627) {
      p = 1;
    } 
    return p;
  }
  static double Nb107f28386(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 1;
    } else if (((Double) i[0]).doubleValue() <= 0.030175493) {
    p = FECTDClassifier.N680c20c4387(i);
    } else if (((Double) i[0]).doubleValue() > 0.030175493) {
    p = FECTDClassifier.N7641047409(i);
    } 
    return p;
  }
  static double N680c20c4387(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.326643795) {
    p = FECTDClassifier.N743b5939388(i);
    } else if (((Double) i[3]).doubleValue() > 0.326643795) {
    p = FECTDClassifier.Ne879ac0392(i);
    } 
    return p;
  }
  static double N743b5939388(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.296733052) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 0.296733052) {
    p = FECTDClassifier.N645ee8cc389(i);
    } 
    return p;
  }
  static double N645ee8cc389(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.027409711) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 0.027409711) {
    p = FECTDClassifier.N1305dc8f390(i);
    } 
    return p;
  }
  static double N1305dc8f390(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.887676469) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() > 0.887676469) {
    p = FECTDClassifier.N65d33a0a391(i);
    } 
    return p;
  }
  static double N65d33a0a391(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 0;
    } else if (((Double) i[2]).doubleValue() <= 0.02657444) {
      p = 0;
    } else if (((Double) i[2]).doubleValue() > 0.02657444) {
      p = 1;
    } 
    return p;
  }
  static double Ne879ac0392(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 1;
    } else if (((Double) i[0]).doubleValue() <= 0.024127721) {
    p = FECTDClassifier.N68c0bab3393(i);
    } else if (((Double) i[0]).doubleValue() > 0.024127721) {
    p = FECTDClassifier.N1e9975b6406(i);
    } 
    return p;
  }
  static double N68c0bab3393(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.430415183) {
    p = FECTDClassifier.N4521737f394(i);
    } else if (((Double) i[3]).doubleValue() > 0.430415183) {
    p = FECTDClassifier.N76f98473397(i);
    } 
    return p;
  }
  static double N4521737f394(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.021997857) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 0.021997857) {
    p = FECTDClassifier.N1725ba4e395(i);
    } 
    return p;
  }
  static double N1725ba4e395(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.376854599) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 0.376854599) {
    p = FECTDClassifier.N2dea62c1396(i);
    } 
    return p;
  }
  static double N2dea62c1396(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 1;
    } else if (((Double) i[2]).doubleValue() <= 0.020092158) {
      p = 1;
    } else if (((Double) i[2]).doubleValue() > 0.020092158) {
      p = 0;
    } 
    return p;
  }
  static double N76f98473397(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.900536051) {
    p = FECTDClassifier.N7766d70a398(i);
    } else if (((Double) i[4]).doubleValue() > 0.900536051) {
    p = FECTDClassifier.N6dc93bd9402(i);
    } 
    return p;
  }
  static double N7766d70a398(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 1;
    } else if (((Double) i[0]).doubleValue() <= 0.02040931) {
    p = FECTDClassifier.Nb00ec4399(i);
    } else if (((Double) i[0]).doubleValue() > 0.02040931) {
      p = 1;
    } 
    return p;
  }
  static double Nb00ec4399(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.467411786) {
    p = FECTDClassifier.N26996636400(i);
    } else if (((Double) i[3]).doubleValue() > 0.467411786) {
    p = FECTDClassifier.N21057706401(i);
    } 
    return p;
  }
  static double N26996636400(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.882681518) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() > 0.882681518) {
      p = 0;
    } 
    return p;
  }
  static double N21057706401(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.894876889) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() > 0.894876889) {
      p = 0;
    } 
    return p;
  }
  static double N6dc93bd9402(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.022163925) {
    p = FECTDClassifier.N36785bde403(i);
    } else if (((Double) i[0]).doubleValue() > 0.022163925) {
      p = 1;
    } 
    return p;
  }
  static double N36785bde403(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.570314944) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 0.570314944) {
    p = FECTDClassifier.N17d767c2404(i);
    } 
    return p;
  }
  static double N17d767c2404(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.903956136) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() > 0.903956136) {
    p = FECTDClassifier.N3edcfdc8405(i);
    } 
    return p;
  }
  static double N3edcfdc8405(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.020956059) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 0.020956059) {
      p = 1;
    } 
    return p;
  }
  static double N1e9975b6406(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() <= 0.907771051) {
      p = 1;
    } else if (((Double) i[4]).doubleValue() > 0.907771051) {
    p = FECTDClassifier.N6cff0f6b407(i);
    } 
    return p;
  }
  static double N6cff0f6b407(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.35583207) {
    p = FECTDClassifier.N5d4d71b4408(i);
    } else if (((Double) i[3]).doubleValue() > 0.35583207) {
      p = 1;
    } 
    return p;
  }
  static double N5d4d71b4408(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.026996388) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() > 0.026996388) {
      p = 1;
    } 
    return p;
  }
  static double N7641047409(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 1;
    } else if (((Double) i[3]).doubleValue() <= 0.272158384) {
    p = FECTDClassifier.N3a68a91b410(i);
    } else if (((Double) i[3]).doubleValue() > 0.272158384) {
      p = 1;
    } 
    return p;
  }
  static double N3a68a91b410(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 0;
    } else if (((Double) i[2]).doubleValue() <= 0.03060437) {
    p = FECTDClassifier.N2d364992411(i);
    } else if (((Double) i[2]).doubleValue() > 0.03060437) {
    p = FECTDClassifier.N2c4cb39413(i);
    } 
    return p;
  }
  static double N2d364992411(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.258280337) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 0.258280337) {
    p = FECTDClassifier.N4718276e412(i);
    } 
    return p;
  }
  static double N4718276e412(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 0;
    } else if (((Double) i[2]).doubleValue() <= 0.0289745) {
      p = 0;
    } else if (((Double) i[2]).doubleValue() > 0.0289745) {
      p = 1;
    } 
    return p;
  }
  static double N2c4cb39413(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 1;
    } else if (((Double) i[3]).doubleValue() <= 0.24429065) {
    p = FECTDClassifier.N45fa0c9a414(i);
    } else if (((Double) i[3]).doubleValue() > 0.24429065) {
      p = 1;
    } 
    return p;
  }
  static double N45fa0c9a414(Object []i) {
    double p = Double.NaN;
    if (i[0] == null) {
      p = 0;
    } else if (((Double) i[0]).doubleValue() <= 0.036038323) {
    p = FECTDClassifier.N2351765a415(i);
    } else if (((Double) i[0]).doubleValue() > 0.036038323) {
      p = 1;
    } 
    return p;
  }
  static double N2351765a415(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.230459213) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() > 0.230459213) {
    p = FECTDClassifier.N393120d3416(i);
    } 
    return p;
  }
  static double N393120d3416(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 52.0) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() > 52.0) {
      p = 1;
    } 
    return p;
  }
  static double N486219fa417(Object []i) {
    double p = Double.NaN;
    if (i[4] == null) {
      p = 0;
    } else if (((Double) i[4]).doubleValue() <= 0.92613043) {
    p = FECTDClassifier.N20979136418(i);
    } else if (((Double) i[4]).doubleValue() > 0.92613043) {
      p = 0;
    } 
    return p;
  }
  static double N20979136418(Object []i) {
    double p = Double.NaN;
    if (i[2] == null) {
      p = 0;
    } else if (((Double) i[2]).doubleValue() <= 0.024246357) {
      p = 0;
    } else if (((Double) i[2]).doubleValue() > 0.024246357) {
    p = FECTDClassifier.N3ebe80f1419(i);
    } 
    return p;
  }
  static double N3ebe80f1419(Object []i) {
    double p = Double.NaN;
    if (i[3] == null) {
      p = 0;
    } else if (((Double) i[3]).doubleValue() <= 0.376476526) {
    p = FECTDClassifier.N4d036e65420(i);
    } else if (((Double) i[3]).doubleValue() > 0.376476526) {
      p = 1;
    } 
    return p;
  }
  static double N4d036e65420(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 56.0) {
    p = FECTDClassifier.N1c44d063421(i);
    } else if (((Double) i[1]).doubleValue() > 56.0) {
      p = 0;
    } 
    return p;
  }
  static double N1c44d063421(Object []i) {
    double p = Double.NaN;
    if (i[1] == null) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() <= 54.0) {
      p = 0;
    } else if (((Double) i[1]).doubleValue() > 54.0) {
      p = 1;
    } 
    return p;
  }
}
