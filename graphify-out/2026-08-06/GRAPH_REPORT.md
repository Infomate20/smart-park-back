# Graph Report - smart-park  (2026-08-05)

## Corpus Check
- 172 files · ~68,241 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 5325 nodes · 16401 edges · 146 communities (142 shown, 4 thin omitted)
- Extraction: 92% EXTRACTED · 8% INFERRED · 0% AMBIGUOUS · INFERRED: 1372 edges (avg confidence: 0.76)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `b98dbb49`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- i
- chunk-2UMSGIQA.js
- chunk-JLYFP63R.js
- main-OWKDLGZI.js
- chunk-NFK5QI6Q.js
- bc
- UtilisateurResponseDto
- CategorieResponseDto
- dp
- chunk-XUE47SUA.js
- .ngOnChanges
- TicketServiceImpl.java
- e
- setTimeout
- .subscribe
- .remove
- updateValueAndValidity
- get
- tv
- a
- .get
- .ngOnDestroy
- chunk-DDEZEMF3.js
- .subscribe
- c
- O
- UtilisateurServiceImpl
- AgenceController
- ImmobilisationController
- Ri
- chunk-5VZHUNVY.js
- ErrorResponse
- ResourceNotFoundException
- Utilisateur
- Transaction
- .init
- _flushAnimations
- ReportController.java
- TransactionController
- v
- chunk-XMOQ7BMQ.js
- e
- InterventionController
- Immobilisation
- .focus
- ImmobilisationResponseDto
- apply
- AgenceServiceImpl
- TransactionServiceImpl
- .getMonth
- le
- av
- chunk-YEVIWNIL.js
- AgenceResponseDto
- attach
- Qa
- .updateStickyColumnStyles
- InterventionRequestDto
- TransactionResponseDto
- constructor
- .constructor
- update
- polyfills-B6TNHZQ6.js
- AgenceRepository
- contains
- InterventionServiceImpl
- Intervention
- chunk-HTANHSLM.js
- UtilisateurResponseDto.java
- Fm
- ze
- UtilisateurRequestDto
- AgenceRequestDto
- AuthController
- InterventionResponseDto
- .clone
- match
- OneTimePassword
- ci
- eo
- DataInitializer.java
- ImmobilisationRequestDto
- updateStickyColumns
- Mf
- command
- un
- chunk-W4AHS3DZ.js
- SecurityConfig.java
- JwtService
- CustomUserPrincipal
- TransactionUpdateDto
- B
- toString
- vi
- ._getCellFromElement
- constructor
- f_
- SchemaAdjustments
- run
- j
- n
- get
- TypeTransaction
- ._markRadiosForCheck
- forEach
- Vs
- LoginRequest
- ci
- position
- Md
- De
- AiQueryController.java
- JwtAuthenticationFilter
- AiQueryService
- onKeydown
- hideTooltip
- AuthResponse
- mvnw
- W
- CodeGenerationServiceImpl
- JwtAuthenticationEntryPoint.java
- toString
- SqlValidatorService
- wn
- AuthenticationException
- .processQuestion
- tu
- ChangementMotDePasseRequestDto
- OtpRequestDto
- Logiciel.java
- OtpValidationRequestDto
- SpaFallbackController.java
- NativeQueryService
- RefreshTokenRequest
- TransactionValidationDto
- GroqService
- StatusTransaction
- SmartParkApplicationTests.java
- SmartParkApplication
- LogicielRepository.java
- smartPark:smart-park
- updatePosition
- toString

## God Nodes (most connected - your core abstractions)
1. `i` - 1097 edges
2. `e` - 281 edges
3. `bc()` - 218 edges
4. `a()` - 130 edges
5. `vy()` - 129 edges
6. `n` - 115 edges
7. `m()` - 103 edges
8. `ResourceNotFoundException` - 95 edges
9. `dp()` - 94 edges
10. `v()` - 89 edges

## Surprising Connections (you probably didn't know these)
- `getEtats()` --indirect_call--> `i`  [INFERRED]
  smart-park/src/main/resources/static/chunk-5VZHUNVY.js → smart-park/src/main/resources/static/chunk-2UMSGIQA.js
- `pl()` --indirect_call--> `i`  [INFERRED]
  smart-park/src/main/resources/static/main-OWKDLGZI.js → smart-park/src/main/resources/static/chunk-2UMSGIQA.js
- `rh()` --indirect_call--> `i`  [INFERRED]
  smart-park/src/main/resources/static/main-OWKDLGZI.js → smart-park/src/main/resources/static/chunk-2UMSGIQA.js
- `y_()` --indirect_call--> `Ql()`  [INFERRED]
  smart-park/src/main/resources/static/chunk-2UMSGIQA.js → smart-park/src/main/resources/static/main-OWKDLGZI.js
- `nw()` --indirect_call--> `on()`  [INFERRED]
  smart-park/src/main/resources/static/chunk-2UMSGIQA.js → smart-park/src/main/resources/static/chunk-JLYFP63R.js

## Import Cycles
- None detected.

## Communities (146 total, 4 thin omitted)

### Community 0 - "i"
Cohesion: 0.01
Nodes (6): i, Xu(), loadAllData(), loadDependencies(), ngOnInit(), wu()

### Community 1 - "chunk-2UMSGIQA.js"
Cohesion: 0.01
Nodes (93): addAsyncValidators(), addSeconds(), addValidators(), bc(), bv(), Cf(), children(), connect() (+85 more)

### Community 2 - "chunk-JLYFP63R.js"
Cohesion: 0.01
Nodes (352): fy(), jm(), ac(), addMatch(), _addToRemovals(), _adjustIndex(), Ae(), af() (+344 more)

### Community 3 - "main-OWKDLGZI.js"
Cohesion: 0.02
Nodes (51): vh(), YE(), Km(), mv(), activate(), appendComponent(), assignDefaults(), brighter() (+43 more)

### Community 4 - "chunk-NFK5QI6Q.js"
Cohesion: 0.02
Nodes (154): aa(), av(), c_(), ca(), Da(), _g(), $h(), j_() (+146 more)

### Community 5 - "bc"
Cohesion: 0.04
Nodes (202): _0(), Ac(), b0(), bw(), C0(), D_(), d0(), e0() (+194 more)

### Community 6 - "UtilisateurResponseDto"
Cohesion: 0.06
Nodes (18): DeleteMapping, GetMapping, Page, Pageable, PreAuthorize, PutMapping, RequestMapping, RequiredArgsConstructor (+10 more)

### Community 7 - "CategorieResponseDto"
Cohesion: 0.12
Nodes (20): CategorieController, CrossOrigin, DeleteMapping, GetMapping, Page, Pageable, PostMapping, PreAuthorize (+12 more)

### Community 8 - "dp"
Cohesion: 0.05
Nodes (43): _addPanelClasses(), am(), appendAll(), applyRedirectCommands(), applyRedirectCreateUrlTree(), attachComponentPortal(), attachTemplatePortal(), cm() (+35 more)

### Community 9 - "chunk-XUE47SUA.js"
Cohesion: 0.04
Nodes (40): animate(), collectEnterElement(), computeStyle(), constructor(), createNamespace(), cs(), di(), disableAnimations() (+32 more)

### Community 10 - ".ngOnChanges"
Cohesion: 0.05
Nodes (19): attachments(), backdropClick(), detachments(), getConfig(), il(), keydownEvents(), Lf(), Oi() (+11 more)

### Community 11 - "TicketServiceImpl.java"
Cohesion: 0.06
Nodes (49): PrePersist, Authentication, GetMapping, Operation, Page, Pageable, PostMapping, PreAuthorize (+41 more)

### Community 12 - "e"
Cohesion: 0.05
Nodes (39): 1. À corriger en priorité côté front, 2.1 Le piège principal, 2.2 Le parcours, en trois appels, 2.3 Durées et rafraîchissement, 2.4 Déconnexion, 2.5 Verrouillage de compte, 2.6 Première connexion, 2.7 En-tête (+31 more)

### Community 13 - "setTimeout"
Cohesion: 0.05
Nodes (4): hasValidator(), hide(), Ii(), zy()

### Community 14 - ".subscribe"
Cohesion: 0.02
Nodes (118): A_(), ap(), kv(), Yp(), ngAfterViewInit(), ad(), afterRun(), Ah() (+110 more)

### Community 15 - ".remove"
Cohesion: 0.03
Nodes (23): addClass(), alignToElement(), appendChild(), applyChanges(), _canBeEnabled(), contains(), destroy(), Ly() (+15 more)

### Community 16 - "updateValueAndValidity"
Cohesion: 0.06
Nodes (53): addControl(), _adjustIndex(), _allControlsDisabled(), _anyControls(), _anyControlsDirty(), _anyControlsHaveStatus(), _anyControlsTouched(), _applyFormState() (+45 more)

### Community 17 - "get"
Cohesion: 0.17
Nodes (12): AllArgsConstructor, Data, NoArgsConstructor, LigneAmortissementDto, AmortissementServiceImpl, Override, Page, Pageable (+4 more)

### Community 19 - "tv"
Cohesion: 0.16
Nodes (19): AmortissementController, GetMapping, Operation, Page, Pageable, PreAuthorize, RequestMapping, RequiredArgsConstructor (+11 more)

### Community 20 - "a"
Cohesion: 0.14
Nodes (21): DetailVehiculeController, DeleteMapping, GetMapping, Operation, Page, Pageable, PreAuthorize, PutMapping (+13 more)

### Community 21 - ".get"
Cohesion: 0.04
Nodes (21): addHandler(), append(), Db(), decoratePreventDefault(), Dy(), Fm(), getError(), getGlobalEventTarget() (+13 more)

### Community 22 - ".ngOnDestroy"
Cohesion: 0.05
Nodes (13): ad(), afterClosed(), bp(), _canClose(), close(), detachBackdrop(), _finishDialogClose(), lr() (+5 more)

### Community 23 - "chunk-DDEZEMF3.js"
Cohesion: 0.21
Nodes (8): AllArgsConstructor, Data, NoArgsConstructor, TransactionRequestDto, TypeTransaction, AFFECTATION, DESAFFECTATION, TRANSFERT

### Community 24 - ".subscribe"
Cohesion: 0.04
Nodes (10): createSubscription(), rr(), skipPredicate(), withAllowedModifierKeys(), withHomeAndEnd(), withHorizontalOrientation(), withPageUpDown(), withVerticalOrientation() (+2 more)

### Community 25 - "c"
Cohesion: 0.37
Nodes (4): AmortissementTest, SpringBootTest, Test, Transactional

### Community 26 - "O"
Cohesion: 0.08
Nodes (55): create(), findTransactions(), allowOnlyTimelineStyles(), appendInstructionToTimeline(), _applyAnimationRefDelays(), applyEmptyStep(), applyStylesToKeyframe(), build() (+47 more)

### Community 27 - "UtilisateurServiceImpl"
Cohesion: 0.10
Nodes (10): ResourceNotFoundException, Override, Page, Pageable, PasswordEncoder, RequiredArgsConstructor, Service, Slf4j (+2 more)

### Community 28 - "AgenceController"
Cohesion: 0.13
Nodes (12): AgenceController, CrossOrigin, DeleteMapping, GetMapping, Page, Pageable, PreAuthorize, PutMapping (+4 more)

### Community 29 - "ImmobilisationController"
Cohesion: 0.14
Nodes (14): ImmobilisationController, CrossOrigin, DeleteMapping, GetMapping, Operation, Page, Pageable, PostMapping (+6 more)

### Community 30 - "Ri"
Cohesion: 0.11
Nodes (21): ah(), b_(), ch(), clampDate(), Co(), compareDate(), dh(), Do() (+13 more)

### Community 31 - "chunk-5VZHUNVY.js"
Cohesion: 0.03
Nodes (46): ab(), cb(), getCookie(), Ib(), _n(), o_(), ob(), Zr() (+38 more)

### Community 32 - "ErrorResponse"
Cohesion: 0.11
Nodes (22): AccessDeniedException, ConstraintViolationException, ExceptionHandler, HttpMessageNotReadableException, HttpRequestMethodNotSupportedException, MethodArgumentNotValidException, MethodArgumentTypeMismatchException, MissingServletRequestParameterException (+14 more)

### Community 33 - "ResourceNotFoundException"
Cohesion: 0.12
Nodes (13): BusinessException, ImmobilisationResponseDto, AllArgsConstructor, Data, NoArgsConstructor, ImmobilisationServiceImpl, Override, Page (+5 more)

### Community 34 - "Utilisateur"
Cohesion: 0.10
Nodes (18): JpaRepository, Modifying, Override, Role, ADMIN, AGENT, TECHNICIEN, AllArgsConstructor (+10 more)

### Community 35 - "Transaction"
Cohesion: 0.13
Nodes (15): EtatTransaction, EN_ATTENTE, REJETEE, VALIDEE, AllArgsConstructor, Data, Entity, NoArgsConstructor (+7 more)

### Community 36 - ".init"
Cohesion: 0.22
Nodes (11): ah(), eo(), ih(), ir(), nh(), Ns(), pi(), rc() (+3 more)

### Community 37 - "_flushAnimations"
Cohesion: 0.11
Nodes (38): absorbOptions(), append(), _balanceNamespaceList(), _beforeAnimationBuild(), _buildAnimation(), containsElement(), create(), destroyActiveAnimationsForElement() (+30 more)

### Community 38 - "ReportController.java"
Cohesion: 0.11
Nodes (22): ResponseStatus, Authentication, PasswordEncoder, PostMapping, PreAuthorize, RequestMapping, RequiredArgsConstructor, ResponseEntity (+14 more)

### Community 39 - "TransactionController"
Cohesion: 0.16
Nodes (12): DeleteMapping, GetMapping, Page, PostMapping, PreAuthorize, PutMapping, RequestMapping, RequiredArgsConstructor (+4 more)

### Community 40 - "v"
Cohesion: 0.10
Nodes (27): DetailVehiculeMapper, Component, DetailVehiculeRequestDto, AllArgsConstructor, Data, NoArgsConstructor, DetailVehicule, AllArgsConstructor (+19 more)

### Community 41 - "chunk-XMOQ7BMQ.js"
Cohesion: 0.07
Nodes (3): ht(), tt(), ngAfterViewInit()

### Community 42 - "e"
Cohesion: 0.08
Nodes (19): An(), As(), br(), ch(), displayable(), e, fr(), _i() (+11 more)

### Community 43 - "InterventionController"
Cohesion: 0.21
Nodes (17): ApiResponse, ApiResponses, InterventionController, CrossOrigin, DeleteMapping, GetMapping, Operation, Page (+9 more)

### Community 44 - "Immobilisation"
Cohesion: 0.12
Nodes (16): EtatImmobilisation, EN_PANNE, EN_REPARATION, EN_SERVICE, MISE_AU_REBUS, Immobilisation, AllArgsConstructor, Data (+8 more)

### Community 45 - ".focus"
Cohesion: 0.09
Nodes (5): Ce(), cl(), dl(), isTyping(), toggle()

### Community 46 - "ImmobilisationResponseDto"
Cohesion: 0.13
Nodes (3): ImmobilisationService, Page, Pageable

### Community 47 - "apply"
Cohesion: 0.11
Nodes (29): apply(), _applyPosition(), _calculateBoundingBoxRect(), _canFitWithFlexibleDimensions(), Cd(), _clearPanelClasses(), ef(), _getExactOverlayX() (+21 more)

### Community 48 - "AgenceServiceImpl"
Cohesion: 0.16
Nodes (8): AgenceServiceImpl, Override, Page, Pageable, RequiredArgsConstructor, Service, Slf4j, Transactional

### Community 49 - "TransactionServiceImpl"
Cohesion: 0.22
Nodes (8): Override, Page, Pageable, RequiredArgsConstructor, Service, Slf4j, Transactional, TransactionServiceImpl

### Community 50 - ".getMonth"
Cohesion: 0.13
Nodes (9): PostMapping, Component, PasswordEncoder, RequiredArgsConstructor, UtilisateurMapper, AllArgsConstructor, Data, NoArgsConstructor (+1 more)

### Community 51 - "le"
Cohesion: 0.18
Nodes (9): Component, CategorieRequestDto, AllArgsConstructor, Data, NoArgsConstructor, MethodeAmortissement, DEGRESSIF, LINEAIRE (+1 more)

### Community 52 - "av"
Cohesion: 0.08
Nodes (28): my(), kr(), Ze(), a(), ei(), scheduleListenerCallback(), ac(), bt() (+20 more)

### Community 53 - "chunk-YEVIWNIL.js"
Cohesion: 0.15
Nodes (8): ImmobilisationMapper, Component, RequiredArgsConstructor, ImmobilisationRequestDto, AllArgsConstructor, Data, NoArgsConstructor, CodeGenerationService

### Community 54 - "AgenceResponseDto"
Cohesion: 0.12
Nodes (8): PostMapping, AgenceResponseDto, AllArgsConstructor, Data, NoArgsConstructor, AgenceService, Page, Pageable

### Community 55 - "attach"
Cohesion: 0.29
Nodes (7): Es(), As(), es(), je(), kt(), pi(), _validateStyleAst()

### Community 56 - "Qa"
Cohesion: 0.08
Nodes (10): cg(), EC(), fl(), fs(), og(), Qa(), rs(), sg() (+2 more)

### Community 57 - ".updateStickyColumnStyles"
Cohesion: 0.09
Nodes (4): Gm(), gs(), wa(), Zh()

### Community 58 - "InterventionRequestDto"
Cohesion: 0.10
Nodes (20): InterventionMapper, Component, InterventionRequestDto, AllArgsConstructor, Data, NoArgsConstructor, InterventionUpdateDto, AllArgsConstructor (+12 more)

### Community 59 - "TransactionResponseDto"
Cohesion: 0.15
Nodes (7): AllArgsConstructor, Data, NoArgsConstructor, TransactionResponseDto, Page, Pageable, TransactionService

### Community 60 - "constructor"
Cohesion: 0.33
Nodes (4): DetailVehiculeTest, SpringBootTest, Test, Transactional

### Community 61 - ".constructor"
Cohesion: 0.06
Nodes (14): applyStyles(), applyToHost(), disconnect(), Ic(), observe(), path(), ri(), setLocale() (+6 more)

### Community 62 - "update"
Cohesion: 0.08
Nodes (32): Ad(), bindWindowResizeEvent(), calculateArc(), calculateLabelPositions(), cloneData(), cr(), data(), ge() (+24 more)

### Community 63 - "polyfills-B6TNHZQ6.js"
Cohesion: 0.07
Nodes (41): constructor(), destroy(), F(), finish(), hasStarted(), init(), onDestroy(), onDone() (+33 more)

### Community 64 - "AgenceRepository"
Cohesion: 0.18
Nodes (11): Agence, AllArgsConstructor, Data, Entity, NoArgsConstructor, Table, AgenceRepository, Page (+3 more)

### Community 65 - "contains"
Cohesion: 0.07
Nodes (3): copyFrom(), getAll(), keys()

### Community 66 - "InterventionServiceImpl"
Cohesion: 0.23
Nodes (8): InterventionServiceImpl, Override, Page, Pageable, RequiredArgsConstructor, Service, Slf4j, Transactional

### Community 67 - "Intervention"
Cohesion: 0.17
Nodes (16): TypeIntervention, DESINSTALLATION, INSTALLATION, MAINTENANCE_CORRECTIVE, MAINTENANCE_PREVENTIVE, Intervention, AllArgsConstructor, Data (+8 more)

### Community 68 - "chunk-HTANHSLM.js"
Cohesion: 0.17
Nodes (15): Ap(), dy(), eo(), go(), Np(), op(), Pa(), rc() (+7 more)

### Community 69 - "UtilisateurResponseDto.java"
Cohesion: 0.26
Nodes (4): ImmobilisationSansNumeroSerieTest, SpringBootTest, Test, Transactional

### Community 70 - "Fm"
Cohesion: 0.10
Nodes (44): Ai(), bm(), clone(), em(), er(), fv(), hc(), ir() (+36 more)

### Community 71 - "ze"
Cohesion: 0.06
Nodes (37): Gs(), Ai(), ao(), bi(), bs(), clamp(), De(), di() (+29 more)

### Community 72 - "UtilisateurRequestDto"
Cohesion: 0.09
Nodes (23): Af(), _assignAsyncValidators(), asyncValidator(), compose(), composeAsync(), hasAsyncValidator(), kd(), Mf() (+15 more)

### Community 73 - "AgenceRequestDto"
Cohesion: 0.13
Nodes (6): AgenceMapper, Component, AgenceRequestDto, AllArgsConstructor, Data, NoArgsConstructor

### Community 74 - "AuthController"
Cohesion: 0.15
Nodes (11): AuthController, CrossOrigin, GetMapping, HttpServletRequest, PostMapping, RequestMapping, RequiredArgsConstructor, ResponseEntity (+3 more)

### Community 75 - "InterventionResponseDto"
Cohesion: 0.19
Nodes (7): InterventionResponseDto, AllArgsConstructor, Data, NoArgsConstructor, InterventionService, Page, Pageable

### Community 76 - ".clone"
Cohesion: 0.06
Nodes (6): compareTime(), deserialize(), eo(), getValidDateOrNull(), sameTime(), zw()

### Community 77 - "match"
Cohesion: 0.17
Nodes (23): capture(), cc(), consumeOptional(), ev(), Ho(), match(), noMatchError(), parse() (+15 more)

### Community 78 - "OneTimePassword"
Cohesion: 0.15
Nodes (14): JavaMailSender, Entity, Getter, Setter, Table, OneTimePassword, OneTimePasswordRepository, EmailService (+6 more)

### Community 79 - "ci"
Cohesion: 0.11
Nodes (27): cv(), dv(), expandSegmentAgainstRouteUsingRedirect(), ey(), getChildConfig(), Gv(), hasChildren(), hm() (+19 more)

### Community 80 - "eo"
Cohesion: 0.17
Nodes (9): CategorieMapper, CategorieServiceImpl, Override, Page, Pageable, RequiredArgsConstructor, Service, Slf4j (+1 more)

### Community 81 - "DataInitializer.java"
Cohesion: 0.14
Nodes (16): CommandLineRunner, DataInitializer, Component, PasswordEncoder, RequiredArgsConstructor, Slf4j, Categorie, AllArgsConstructor (+8 more)

### Community 82 - "ImmobilisationRequestDto"
Cohesion: 0.12
Nodes (8): getParentElement(), Ms(), n, normalizePropertyName(), qe(), query(), Ve(), ye()

### Community 83 - "updateStickyColumns"
Cohesion: 0.16
Nodes (15): _addStickyStyle(), clearStickyPositioning(), _getCalculatedZIndex(), _getCellWidths(), _getStickyEndColumnPositions(), _getStickyStartColumnPositions(), Py(), _removeFromStickyColumnReplayQueue() (+7 more)

### Community 84 - "Mf"
Cohesion: 0.15
Nodes (12): Be(), fi(), ge(), _getPlayer(), Gt(), Ht(), Ie(), jt() (+4 more)

### Community 85 - "command"
Cohesion: 0.18
Nodes (16): beforeDestroy(), _buildPlayer(), command(), _convertKeyframesToObject(), finish(), hasStarted(), init(), _onFinish() (+8 more)

### Community 86 - "un"
Cohesion: 0.14
Nodes (12): by(), vy(), yy(), Al(), ar(), Cn(), createElement(), Gc() (+4 more)

### Community 88 - "SecurityConfig.java"
Cohesion: 0.21
Nodes (13): AuthenticationConfiguration, AuthenticationProvider, Bean, Configuration, CorsConfigurationSource, EnableMethodSecurity, EnableWebSecurity, HttpSecurity (+5 more)

### Community 89 - "JwtService"
Cohesion: 0.22
Nodes (5): Claims, Key, Service, UserDetails, JwtService

### Community 90 - "CustomUserPrincipal"
Cohesion: 0.17
Nodes (8): GrantedAuthority, CustomUserDetailsService, CustomUserPrincipal, Override, RequiredArgsConstructor, Service, UserDetails, UserDetailsService

### Community 91 - "TransactionUpdateDto"
Cohesion: 0.38
Nodes (6): AuthServiceImpl, AuthenticationManager, Override, RequiredArgsConstructor, Service, Transactional

### Community 92 - "B"
Cohesion: 0.14
Nodes (15): al(), Fd(), hf(), If(), jw(), kf(), nt(), pD() (+7 more)

### Community 93 - "toString"
Cohesion: 0.07
Nodes (32): addHeaderEntry(), applyUpdate(), _assignValidators(), attachAnchors(), constructor(), _createAnchor(), createElement(), decodeKey() (+24 more)

### Community 94 - "vi"
Cohesion: 0.15
Nodes (11): AI assistant (`assistance/`), Architecture, Auth (two-step, JWT), Commands, Configuration, Domain, Errors, graphify (+3 more)

### Community 95 - "._getCellFromElement"
Cohesion: 0.12
Nodes (3): Rg(), Zd(), Qd()

### Community 96 - "constructor"
Cohesion: 0.29
Nodes (10): bh(), cl(), delete(), get(), getDashboardData(), has(), mo(), processTransactionStats() (+2 more)

### Community 97 - "f_"
Cohesion: 0.20
Nodes (15): Bt(), eh(), Ei(), f_(), ih(), lh(), nh(), oh() (+7 more)

### Community 98 - "SchemaAdjustments"
Cohesion: 0.39
Nodes (5): DataSource, JdbcTemplate, SpringBootTest, Test, MigrationDepuisZeroTest

### Community 99 - "run"
Cohesion: 0.22
Nodes (10): fc(), jo(), nm(), rm(), serialize(), Vo(), Wb(), Yb() (+2 more)

### Community 100 - "j"
Cohesion: 0.12
Nodes (18): openFormDialog(), create(), findTickets(), copy(), Ct(), ic(), j(), jc() (+10 more)

### Community 101 - "n"
Cohesion: 0.20
Nodes (10): bs(), bt(), match(), matchTransition(), normalizeStyleValue(), ti(), ws(), ze() (+2 more)

### Community 102 - "get"
Cohesion: 0.15
Nodes (19): afterFlush(), afterFlushAnimationsDone(), clearElementCache(), createRenderer(), Ct(), destroy(), _fetchNamespace(), fetchNamespacesByElement() (+11 more)

### Community 103 - "TypeTransaction"
Cohesion: 0.39
Nodes (9): _addAfter(), _addIdentityChange(), _addToMoves(), check(), _insertAfter(), _mismatch(), _moveAfter(), _reinsertAfter() (+1 more)

### Community 104 - "._markRadiosForCheck"
Cohesion: 0.36
Nodes (4): EtiquetteService, EtiquetteImpl, RequiredArgsConstructor, Service

### Community 105 - "forEach"
Cohesion: 0.29
Nodes (12): clear(), deselect(), _getConcreteValue(), _hasQueuedChanges(), hasValue(), isSelected(), _markSelected(), select() (+4 more)

### Community 106 - "Vs"
Cohesion: 0.15
Nodes (13): appendChild(), _c(), fl(), gl(), Hs(), On(), pn(), Q() (+5 more)

### Community 107 - "LoginRequest"
Cohesion: 0.48
Nodes (5): AllArgsConstructor, Builder, Data, NoArgsConstructor, LoginRequest

### Community 108 - "ci"
Cohesion: 0.09
Nodes (21): activate(), activateChildRoutes(), activateRoutes(), ci(), deactivateChildRoutes(), deactivateRouteAndItsChildren(), deactivateRouteAndOutlet(), deactivateRoutes() (+13 more)

### Community 109 - "position"
Cohesion: 0.17
Nodes (12): Mo(), calculateHorizontalAlignment(), calculateHorizontalCaret(), calculateVerticalAlignment(), calculateVerticalCaret(), checkFlip(), determinePlacement(), onWindowResize() (+4 more)

### Community 111 - "Md"
Cohesion: 0.11
Nodes (17): dd(), email(), iD(), _invokeOnDestroyCallbacks(), Md(), minLength(), Nl(), registerOnChange() (+9 more)

### Community 112 - "De"
Cohesion: 0.60
Nodes (5): AllArgsConstructor, Builder, Data, NoArgsConstructor, RefreshTokenRequest

### Community 113 - "AiQueryController.java"
Cohesion: 0.30
Nodes (10): AiQueryController, AiQueryRequest, Getter, PostMapping, PreAuthorize, RequestMapping, RequiredArgsConstructor, ResponseEntity (+2 more)

### Community 114 - "JwtAuthenticationFilter"
Cohesion: 0.31
Nodes (9): FilterChain, OncePerRequestFilter, Component, HttpServletRequest, HttpServletResponse, Override, RequiredArgsConstructor, UserDetailsService (+1 more)

### Community 115 - "AiQueryService"
Cohesion: 0.31
Nodes (8): ObjectMapper, AiQueryService, Logger, RequiredArgsConstructor, Service, Slf4j, Component, PromptFactory

### Community 116 - "onKeydown"
Cohesion: 0.31
Nodes (13): _getItemsArray(), onKeydown(), _setActiveInDefaultMode(), _setActiveInWrapMode(), setActiveItem(), _setActiveItemByDelta(), _setActiveItemByIndex(), setFirstItemActive() (+5 more)

### Community 117 - "hideTooltip"
Cohesion: 0.07
Nodes (33): ay(), mm(), afterDismissed(), afterOpened(), closeWithAction(), dismiss(), _dismissAfter(), dismissWithAction() (+25 more)

### Community 118 - "AuthResponse"
Cohesion: 0.60
Nodes (5): AuthResponse, AllArgsConstructor, Builder, Data, NoArgsConstructor

### Community 119 - "mvnw"
Cohesion: 0.33
Nodes (6): mvnw script, clean(), die(), exec_maven(), set_java_home(), verbose()

### Community 120 - "W"
Cohesion: 0.33
Nodes (6): Fh(), In(), Lc(), Rn(), St(), W()

### Community 121 - "CodeGenerationServiceImpl"
Cohesion: 0.36
Nodes (5): CodeGenerationServiceImpl, Override, RequiredArgsConstructor, Service, Slf4j

### Community 123 - "JwtAuthenticationEntryPoint.java"
Cohesion: 0.36
Nodes (7): AuthenticationEntryPoint, AuthenticationException, Component, HttpServletRequest, HttpServletResponse, Override, JwtAuthenticationEntryPoint

### Community 124 - "toString"
Cohesion: 0.70
Nodes (4): ImmobilisationLightDto, AllArgsConstructor, Data, NoArgsConstructor

### Community 125 - "SqlValidatorService"
Cohesion: 0.83
Nodes (3): Pattern, Service, SqlValidatorService

### Community 126 - "wn"
Cohesion: 0.50
Nodes (5): Ce(), Pe(), wn(), Xn(), zn()

### Community 127 - "AuthenticationException"
Cohesion: 0.38
Nodes (3): AuthenticationException, AuthenticationFailedException, InvalidTokenException

### Community 128 - ".processQuestion"
Cohesion: 0.38
Nodes (3): AiTrainingLog, AllArgsConstructor, Getter

### Community 129 - "tu"
Cohesion: 0.27
Nodes (10): _executeOnStable(), focusFirstTabbableElement(), focusFirstTabbableElementWhenReady(), focusInitialElement(), focusInitialElementWhenReady(), focusLastTabbableElement(), focusLastTabbableElementWhenReady(), _getFirstTabbableElement() (+2 more)

### Community 130 - "ChangementMotDePasseRequestDto"
Cohesion: 0.53
Nodes (4): ChangementMotDePasseRequestDto, AllArgsConstructor, Data, NoArgsConstructor

### Community 131 - "OtpRequestDto"
Cohesion: 0.83
Nodes (3): Getter, Setter, OtpRequestDto

### Community 132 - "Logiciel.java"
Cohesion: 0.52
Nodes (6): AllArgsConstructor, Builder, Data, Entity, NoArgsConstructor, Logiciel

### Community 133 - "OtpValidationRequestDto"
Cohesion: 0.83
Nodes (3): Getter, Setter, OtpValidationRequestDto

### Community 134 - "SpaFallbackController.java"
Cohesion: 0.53
Nodes (4): Controller, Order, RequestMapping, SpaFallbackController

### Community 135 - "NativeQueryService"
Cohesion: 0.53
Nodes (4): EntityManager, Service, Transactional, NativeQueryService

### Community 136 - "RefreshTokenRequest"
Cohesion: 0.83
Nodes (3): AllArgsConstructor, Getter, LoginStep1ResponseDto

### Community 137 - "TransactionValidationDto"
Cohesion: 0.15
Nodes (10): Component, TransactionMapper, AllArgsConstructor, Data, NoArgsConstructor, TransactionUpdateDto, AllArgsConstructor, Data (+2 more)

### Community 140 - "GroqService"
Cohesion: 0.70
Nodes (4): RestTemplate, GroqService, Logger, Service

### Community 143 - "StatusTransaction"
Cohesion: 0.40
Nodes (4): StatusTransaction, EN_ATTENTE, REJETEE, VALIDEE

### Community 145 - "SmartParkApplicationTests.java"
Cohesion: 0.60
Nodes (3): SpringBootTest, Test, SmartParkApplicationTests

### Community 156 - "updatePosition"
Cohesion: 0.09
Nodes (31): addPanelClass(), attach(), _attachBackdrop(), bottom(), centerHorizontally(), centerVertically(), _completeDetachContent(), detach() (+23 more)

### Community 160 - "toString"
Cohesion: 0.29
Nodes (7): bc(), dh(), mr(), no(), toString(), uo(), Xe()

## Knowledge Gaps
- **85 isolated node(s):** `smartPark:smart-park`, `EN_SERVICE`, `EN_PANNE`, `EN_REPARATION`, `MISE_AU_REBUS` (+80 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **4 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `i` connect `i` to `chunk-2UMSGIQA.js`, `chunk-JLYFP63R.js`, `main-OWKDLGZI.js`, `chunk-NFK5QI6Q.js`, `bc`, `dp`, `chunk-XUE47SUA.js`, `.ngOnChanges`, `setTimeout`, `.subscribe`, `.remove`, `updateValueAndValidity`, `.get`, `.ngOnDestroy`, `.subscribe`, `O`, `updatePosition`, `Ri`, `chunk-5VZHUNVY.js`, `.init`, `_flushAnimations`, `chunk-XMOQ7BMQ.js`, `e`, `.focus`, `apply`, `av`, `Qa`, `.updateStickyColumnStyles`, `.constructor`, `update`, `polyfills-B6TNHZQ6.js`, `contains`, `chunk-HTANHSLM.js`, `Fm`, `ze`, `UtilisateurRequestDto`, `.clone`, `ImmobilisationRequestDto`, `updateStickyColumns`, `Mf`, `command`, `un`, `chunk-W4AHS3DZ.js`, `B`, `toString`, `._getCellFromElement`, `constructor`, `j`, `n`, `get`, `forEach`, `ci`, `position`, `._bindTransitionEvents`, `Md`, `hideTooltip`, `._focusActiveCell`?**
  _High betweenness centrality (0.180) - this node is a cross-community bridge._
- **Why does `ResourceNotFoundException` connect `UtilisateurServiceImpl` to `TransactionValidationDto`, `TicketServiceImpl.java`, `get`, `chunk-DDEZEMF3.js`, `ErrorResponse`, `ResourceNotFoundException`, `ReportController.java`, `v`, `Immobilisation`, `AgenceServiceImpl`, `TransactionServiceImpl`, `.getMonth`, `chunk-YEVIWNIL.js`, `constructor`, `InterventionServiceImpl`, `AgenceRequestDto`, `OneTimePassword`, `eo`, `TransactionUpdateDto`, `._markRadiosForCheck`?**
  _High betweenness centrality (0.030) - this node is a cross-community bridge._
- **Why does `e` connect `.subscribe` to `i`, `chunk-2UMSGIQA.js`, `tu`, `chunk-JLYFP63R.js`, `chunk-NFK5QI6Q.js`, `bc`, `dp`, `chunk-XUE47SUA.js`, `.ngOnChanges`, `setTimeout`, `.remove`, `updateValueAndValidity`, `.get`, `.ngOnDestroy`, `.subscribe`, `O`, `updatePosition`, `Ri`, `chunk-5VZHUNVY.js`, `_flushAnimations`, `chunk-XMOQ7BMQ.js`, `.focus`, `apply`, `av`, `.updateStickyColumnStyles`, `.constructor`, `contains`, `Fm`, `UtilisateurRequestDto`, `ci`, `Mf`, `command`, `chunk-W4AHS3DZ.js`, `B`, `toString`, `._getCellFromElement`, `f_`, `run`, `get`, `ci`, `._bindTransitionEvents`, `Md`, `hideTooltip`, `._focusActiveCell`?**
  _High betweenness centrality (0.018) - this node is a cross-community bridge._
- **Are the 95 inferred relationships involving `i` (e.g. with `.forChild()` and `.forRoot()`) actually correct?**
  _`i` has 95 INFERRED edges - model-reasoned connections that need verification._
- **Are the 116 inferred relationships involving `e` (e.g. with `_addPanelClasses()` and `Af()`) actually correct?**
  _`e` has 116 INFERRED edges - model-reasoned connections that need verification._
- **Are the 118 inferred relationships involving `a()` (e.g. with `aa()` and `activateChildRoutes()`) actually correct?**
  _`a()` has 118 INFERRED edges - model-reasoned connections that need verification._
- **What connects `smartPark:smart-park`, `EN_SERVICE`, `EN_PANNE` to the rest of the system?**
  _85 weakly-connected nodes found - possible documentation gaps or missing edges._