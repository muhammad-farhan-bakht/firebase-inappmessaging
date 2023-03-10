// Copyright 2018 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.firebase.inappmessaging;

import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.connector.AnalyticsConnector;
import com.google.firebase.annotations.concurrent.Blocking;
import com.google.firebase.inappmessaging.internal.DeveloperListenerManager;
import com.google.firebase.inappmessaging.internal.MetricsLoggerClient;
import com.google.firebase.inappmessaging.internal.MetricsLoggerClient.EngagementMetricsLoggerInterface;
import com.google.firebase.inappmessaging.internal.injection.scopes.FirebaseAppScope;
import com.google.firebase.inappmessaging.internal.time.Clock;
import com.google.firebase.installations.FirebaseInstallationsApi;
import dagger.Module;
import dagger.Provides;
import java.util.concurrent.Executor;

@Module
public class TestEngagementMetricsLoggerClientModule {
  private final FirebaseApp firebaseApp;
  private final EngagementMetricsLoggerInterface engagementMetricsLoggerInterface;

  public TestEngagementMetricsLoggerClientModule(
      FirebaseApp firebaseApp, EngagementMetricsLoggerInterface engagementMetricsLoggerInterface) {
    this.firebaseApp = firebaseApp;
    this.engagementMetricsLoggerInterface = engagementMetricsLoggerInterface;
  }

  @Provides
  @FirebaseAppScope
  public MetricsLoggerClient providesMetricLoggerClient(
      FirebaseInstallationsApi firebaseInstallations,
      AnalyticsConnector analyticsConnector,
      Clock clock,
      DeveloperListenerManager developerListenerManager,
      @Blocking Executor blockingExecutor) {
    return new MetricsLoggerClient(
        engagementMetricsLoggerInterface,
        analyticsConnector,
        firebaseApp,
        firebaseInstallations,
        clock,
        developerListenerManager,
        blockingExecutor);
  }
}
