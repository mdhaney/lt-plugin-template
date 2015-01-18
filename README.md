# lt-plugin

A Leiningen template to generate LightTable plugin projects.

## Quick Start

[Light Table plugin development quick start](http://youtu.be/j91Li9kL0t8)

## Usage

From [your LightTable plugins folder](http://docs.lighttable.com/#plugins-directory), run:

`lein new lt-plugin myplugin`

Options:
`-j` - generate plugin.json instead of plugin.edn

## Building

* Add the `myplugin` folder to LightTable and open the `myplugin.cljs` file.

* Run the command `Editor: Build file or project` (you should see "Compiled plugin to ...myplugin_compiled.js" in the statusbar)

To load/reload the plugin:

* Run the command `Plugins: Refresh plugin list`
* Run the command `App: Reload behaviors`

The plugin will also be loaded on restarting Light Table.

## Testing

* Run the command `myplugin: Say Hello`


## License

Copyright © 2014 Mike Haney

Distributed under the Eclipse Public License 1.0
